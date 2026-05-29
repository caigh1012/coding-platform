import React, { SyntheticEvent, useState } from 'react';
import TextArea from 'antd/es/input/TextArea';
import { Button } from 'antd';

import { useStreamMessage } from '@/core/hooks/use-stream-message';

import './agent.scss';

const AiAgent: React.FC = () => {
  const [value, setValue] = useState('');

  const { content, isLoading, error, sendMessage } = useStreamMessage();

  function getChat() {
    // aiChat(value).then((res) => {
    //   console.log(res);
    // });
    sendMessage(value);
  }

  function getInputVal(e: SyntheticEvent) {
    setValue(e.target?.value || '');
  }

  return (
    <div styleName="wrapper">
      <div styleName="chat-wrapper">
        {/* {messages.map((msg) => (
          <div
            key={msg.id}
            styleName={clsx({ 'chat-box': true, bot: msg.isBot, user: !msg.isBot })}>
            <div styleName="chat">
              <div styleName={clsx({ 'avatar-box': true, bot: msg.isBot, user: !msg.isBot })}>
                <Avatar style={{ backgroundColor: '#fde3cf', color: '#f56a00' }}>{msg.isBot ? 'Bot' : 'User'}</Avatar>
              </div>
              <div styleName="message-box">{msg.message}</div>
            </div>
          </div>
        ))} */}
        {content || <span style={{ color: '#999' }}>{isLoading ? 'AI 正在思考...' : '输入问题开始对话'}</span>}
        {/* {loading && <div>思考中...</div>} */}

        {/* 错误提示 */}
        {error && <div style={{ color: 'red', marginBottom: 8 }}>错误：{error}</div>}
      </div>
      <div styleName="send-box">
        <TextArea
          rows={2}
          value={value}
          onChange={(e) => getInputVal(e)}
        />
        <Button
          type="primary"
          onClick={() => getChat()}>
          发送
        </Button>
      </div>
    </div>
  );
};

export default AiAgent;
