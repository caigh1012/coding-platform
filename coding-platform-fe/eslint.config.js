const path = require('path');
const jsEsint = require('@eslint/js');
const globals = require('globals');
const tseslint = require('typescript-eslint');
const jsxA11y = require('eslint-plugin-jsx-a11y');
const reactPlugin = require('eslint-plugin-react');
const importPlugin = require('eslint-plugin-import');

module.exports = [
  {
    ignores: ['node_modules/**/*', 'dist/**/*', 'build/**/*'], // 忽略目录
  },
  {
    files: ['**/*.js'],
    ignores: ['scripts/husky.js', 'mock/index.js'],
    languageOptions: {
      sourceType: 'commonjs',
      globals: globals.node,
    },
    plugins: {
      prettier: require('eslint-plugin-prettier'),
    },
    rules: {
      ...jsEsint.configs.recommended.rules,
      'prettier/prettier': 'error',
      'no-console': 'error',
    },
  },
  {
    files: ['**/*.{ts,tsx}'],
    languageOptions: {
      parser: require('@typescript-eslint/parser'),
      sourceType: 'module',
      globals: {
        ...globals.serviceworker,
        ...globals.browser,
      },
      parserOptions: {
        ecmaFeatures: {
          jsx: true,
        },
      },
    },
    linterOptions: {
      noInlineConfig: false,
    },
    settings: {
      'import/resolver': {
        alias: {
          map: [['@', path.resolve(__dirname, 'src')]],
          extensions: ['.ts', '.tsx', '.js'],
        },
      },
    },
    plugins: {
      prettier: require('eslint-plugin-prettier'),
      '@typescript-eslint': require('@typescript-eslint/eslint-plugin'),
      react: reactPlugin,
      import: importPlugin,
      'jsx-a11y': jsxA11y,
    },
    rules: {
      ...tseslint.configs.recommended.rules,
      ...reactPlugin.configs.recommended.rules,
      ...jsxA11y.configs.recommended.rules,
      'prettier/prettier': 'error',
      '@typescript-eslint/no-explicit-any': 'error',
      'react/react-in-jsx-scope': 0,
      'react/no-unknown-property': ['error', { ignore: ['styleName'] }],
      'no-console': 'error',
    },
  },
];
