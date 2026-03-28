// eslint-disable-next-line @typescript-eslint/no-explicit-any
declare type SafeAny = any; // 定义第三方或复杂项目时定义 SafeAny

// 为图片定义模块
declare module '*.svg';
declare module '*.png';
declare module '*.jpg';

declare const Version: string;
declare const Env: string;

// 项目配置注入变量类型
declare const apiUrl: string;
declare const apiPrefix: string;
declare const baseHref: string;
declare const captchaAppId: string;

// 腾讯云滑块类型

/**
 * 腾讯云验证码 (TCaptcha) 类型声明
 * @description 适用于通过 https://ssl.captcha.qq.com/TCaptcha.js 引入的全局 TencentCaptcha 类
 */
declare namespace TencentCaptcha {
  /**
   * 验证码回调结果
   */

  /**
   * 验证码回调函数
   */
  type Callback = (res: CallbackResult) => void;

  /**
   * SDK 配置选项
   */
  interface Options {
    /** 自定义透传参数，业务可用该字段传递少量数据，该字段的内容会被带入 callback 回调的对象中 */
    bizState?: SafeAny;
    /** 开启自适应深夜模式 */
    enableDarkMode?: boolean;
    /**
     * SDK 参数 (主要在移动端原生 WebView 中使用)
     * 例如: { width: 140, height: 140 } 设置 loading 弹框大小
     */
    sdkOpts?: {
      width: number;
      height: number;
    };
    aidEncrypted: string;
    /**
     * 验证码加载完成的回调
     * @param param - 包含验证码实际宽高的对象
     */
    ready?: (param: { sdkView: { width: number; height: number } }) => void;
  }

  interface CallbackResult {
    /** 验证结果，0：验证成功，2：用户主动关闭验证码 */
    ret: 0 | 2;
    /** 验证成功的票据，当且仅当 ret = 0 时 ticket 有值 */
    ticket: string;
    /** 验证码应用 ID */
    appid: string;
    /** 自定义透传参数，原样返回 */
    bizState?: SafeAny;
    /** 本次验证的随机串，后续票据校验时需传递该参数 */
    randstr: string;
    /** 错误码，详情参见官方文档 */
    errorCode?: number;
    /** 错误信息 */
    errorMessage?: string;
  }
}

/**
 * TencentCaptcha 类定义
 */
declare class TencentCaptcha {
  /**
   * 创建一个验证码实例
   * @param appid - 在腾讯云验证码控制台获取的 CaptchaAppId
   * @param callback - 验证完成后的回调函数
   * @param options - 可选配置参数
   */
  constructor(appid: string, callback: TencentCaptcha.Callback, options?: TencentCaptcha.Options);

  /**
   * 唤起验证码窗口
   * @param bizState - 可选，用于覆盖实例化时传入的 bizState
   */
  show(bizState?: SafeAny): void;
}
