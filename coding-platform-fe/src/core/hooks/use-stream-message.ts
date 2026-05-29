import { useState, useCallback } from 'react';

export function useStreamMessage() {
  const [content, setContent] = useState(''); // 当前累积的文本
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState(null);

  const sendMessage = useCallback(async (message: string) => {
    // 重置状态
    setContent('');
    setError(null);
    setIsLoading(true);

    try {
      const response = await fetch('/api/ai/chat.json', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          Accept: 'text/event-stream',
        },
        body: JSON.stringify({ message }),
      });

      if (!response.ok) {
        throw new Error(`请求失败：${response.status}`);
      }

      const reader = response.body.getReader();
      const decoder = new TextDecoder();
      let buffer = '';

      while (true) {
        const { done: streamDone, value } = await reader.read();

        if (streamDone) break;

        // 解码新收到的一批数据
        buffer += decoder.decode(value, { stream: true });
        const lines = buffer.split('\n');
        buffer = lines.pop(); // 保留不完整行

        for (const line of lines) {
          if (line.startsWith('data:')) {
            const dataStr = line.substring(5).trim();
            if (!dataStr) continue;

            try {
              const chunk = JSON.parse(dataStr);

              // 追加 message 字段到累积文本
              if (chunk.message) {
                setContent((prev) => prev + chunk.message);
              }
              // 收到结束标志，停止读取
              if (chunk.done) {
                // 直接跳出循环，结束读取
                return; // 退出 sendMessage 函数
              }
            } catch (_e: SafeAny) {
              // 若解析失败，丢弃该数据（或记录日志）
              // eslint-disable-next-line no-console
              console.warn('SSE 解析失败：', dataStr);
            }
          }
        }
      }
    } catch (err: SafeAny) {
      if (err.name === 'AbortError') {
        // 正常取消，不做处理
        // eslint-disable-next-line no-console
        console.log('请求已取消');
      } else {
        setError(err.message);
      }
    } finally {
      setIsLoading(false);
    }
  }, []);

  return { content, isLoading, error, sendMessage };
}
