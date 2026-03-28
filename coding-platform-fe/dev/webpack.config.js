const path = require('path');
const webpack = require('webpack');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const TerserPlugin = require('terser-webpack-plugin');
const MiniCssExtractPlugin = require('mini-css-extract-plugin');
const CssMinimizerPlugin = require('css-minimizer-webpack-plugin');
const { getLocalIdent } = require('@dr.pogodin/babel-plugin-react-css-modules/utils');
const ReactRefreshPlugin = require('@pmmmwh/react-refresh-webpack-plugin');
const { BundleAnalyzerPlugin } = require('webpack-bundle-analyzer');

// 获取 package.json 信息
const pkg = require('../package.json');

// 获取项目生产运维配置
const config = require('../config/coding-platform-fe.json');

/**
 * 加载dev环境的环境变量
 */
require('@dotenvx/dotenvx').config({ path: ['.env.development'] });

// 当前关联目录
const contentRelativePath = '../';

const isDevMode = process.env.NODE_ENV === 'development';

const globalVarCfg = {
  ...config,
  baseHref: isDevMode ? '/' : config.baseHref,
  Version: pkg.version,
  Env: process.env.NODE_ENV,
};

/**
 * 定义 DefinePlugin 注入的变量
 */
const defineCfg = {};
Object.entries(globalVarCfg).forEach(([k, v]) => {
  defineCfg[k] = JSON.stringify(v);
});

/**
 * entry 配置
 */
const entryList = require('./entry');

const createEntry = () => {
  return entryList.reduce((prev, curr) => {
    prev[curr.name] = curr.entry;
    return prev;
  }, {});
};

// ScssModule
const parseScssModule = (options = {}) => {
  const { modules } = options; // # modules 参数处理react的样式模块化，对全局样式不做样式模块化处理

  const cssLoaderOptions = {
    sourceMap: isDevMode,
    modules: false,
  };

  if (modules) {
    Object.assign(cssLoaderOptions, {
      importLoaders: 1,
      modules: {
        namedExport: true,
        localIdentContext: path.resolve(__dirname, 'src'),
        getLocalIdent,
        localIdentName: '[name]__[local]__[contenthash:base64:5]',
      },
    });
  }

  return [
    isDevMode ? 'style-loader' : MiniCssExtractPlugin.loader,
    {
      loader: 'css-loader',
      options: cssLoaderOptions,
    },
    {
      loader: 'sass-loader',
      options: { sourceMap: isDevMode },
    },
  ];
};

const getModulesRules = () => [
  {
    test: /\.(jpg|gif|png|svg|ico)$/,
    type: 'asset',
    parser: {
      dataUrlCondition: {
        maxSize: 8 * 1024,
      },
    },
    generator: {
      filename: 'images/[name].[hash:8][ext]',
    },
  },
  {
    test: /\.(t|j)sx?$/,
    include: path.resolve(__dirname, contentRelativePath, 'src'),
    exclude: /node_modules/,
    use: [
      {
        loader: 'babel-loader',
        options: {
          cacheDirectory: !isDevMode,
          plugins: [isDevMode && require.resolve('react-refresh/babel')].filter(Boolean),
        },
      },
      {
        loader: 'thread-loader',
        options: {
          workers: 4,
          workerParallelJobs: 50,
          workerNodeArgs: ['--max-old-space-size=1024'],
          poolTimeout: 2000,
          poolParallelJobs: 50,
          name: 'my-pool',
        },
      },
      {
        loader: 'ts-loader',
        options: {
          happyPackMode: true,
          transpileOnly: true, // 不进行类型检查,
        },
      },
    ],
  },
  {
    test: /\.scss$/,
    include: path.resolve(__dirname, contentRelativePath, 'src/styles/'),
    use: parseScssModule(),
  },
  {
    test: /\.scss$/,
    exclude: [/node_modules/, path.resolve(__dirname, contentRelativePath, 'src/styles/')],
    use: parseScssModule({ modules: true }),
  },
];

const getPlugins = () => [
  !isDevMode && process.env.isBundleAnalyzer === '1' && new BundleAnalyzerPlugin(),
  ...entryList.map(
    (i) =>
      new HtmlWebpackPlugin({
        inject: 'body',
        chunks: [i.name],
        filename: `${i.name}.html`,
        title: i.title || 'React App',
        template: i.template || '../template/index.html',
        templateParameters: {
          reactVersion: pkg.dependencies.react,
        },
        favicon: path.resolve(__dirname, contentRelativePath, './public/favicon.ico'),
        hash: true,
      }),
  ),
  new webpack.DefinePlugin(defineCfg),
  isDevMode && new ReactRefreshPlugin(),
];

const getDevServer = () => {
  let targetProxy = process.env.targetProxy;
  let apiUrl = process.env[targetProxy];
  return {
    compress: true,
    historyApiFallback: {
      disableDotRule: true,
    },
    hot: true,
    port: process.env.serverPort,
    host: '0.0.0.0',
    proxy: [
      {
        context: [config.apiPrefix],
        target: apiUrl,
        changeOrigin: true,
        secure: false,
      },
    ],
  };
};

/**
 * 基础配置
 */
const baseCfg = {
  context: path.resolve(__dirname, '../'),
  entry: createEntry(),
  output: {
    path: path.resolve(__dirname, contentRelativePath, `dist${globalVarCfg.baseHref}`),
    filename: isDevMode ? 'js/[name].js' : 'js/[name].[contenthash].js',
    clean: true,
    publicPath: globalVarCfg.baseHref,
    chunkFilename: 'chunks/[name].[contenthash].js',
  },
  plugins: getPlugins().filter(Boolean),
  module: {
    rules: getModulesRules(),
  },
  devServer: getDevServer(),
  resolve: {
    extensions: ['.ts', '.tsx', '.jsx', '.js'],
    alias: {
      '@': path.resolve(__dirname, contentRelativePath, './src'),
    },
  },
  performance: {
    hints: false,
  },
  mode: isDevMode ? 'development' : 'production',
};

/**
 * dev环境配置
 */
const devCfg = {
  devServer: getDevServer(),
  devtool: 'cheap-source-map',
};

/**
 * prod环境配置
 */
const prodCfg = {
  optimization: {
    splitChunks: {
      chunks: 'all',
      name: 'vendor',
      cacheGroups: {
        vendors: {
          test: /[\\/]node_modules[\\/]/,
        },
      },
    },
    emitOnErrors: true,
    usedExports: true, // tree shaking settings
    minimize: true,
    minimizer: [
      new TerserPlugin({
        parallel: true,
        terserOptions: {
          format: {
            comments: false,
          },
        },
      }),
      new CssMinimizerPlugin(),
    ],
  },
};

const webpackCfg = Object.assign(baseCfg, isDevMode && devCfg, !isDevMode && prodCfg);

if (!isDevMode) {
  webpackCfg.plugins.push(
    new MiniCssExtractPlugin({
      filename: 'css/[name].[contenthash:5].css',
      chunkFilename: 'css/[id].[contenthash:5].css',
    }),
  );
}

module.exports = webpackCfg;
