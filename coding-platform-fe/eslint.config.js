// const path = require('path');
const { defineConfig, globalIgnores } = require('eslint/config');
const globals = require('globals');

const prettier = require('eslint-plugin-prettier');
const js = require('@eslint/js');
const ts = require('typescript-eslint');
const importPlugin = require('eslint-plugin-import');
const jsxA11y = require('eslint-plugin-jsx-a11y');
const react = require('eslint-plugin-react');
const reactHooks = require('eslint-plugin-react-hooks');

module.exports = defineConfig([
  globalIgnores(['node_modules/**/*', 'dist/**/*', 'build/**/*']),
  {
    files: ['**/*.js'],
    languageOptions: {
      sourceType: 'commonjs',
      globals: globals.node,
    },
    plugins: {
      js,
      prettier,
    },
    rules: {
      ...js.configs.recommended.rules,
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
        ENV: true,
        APIURL: true,
        baseHref: true,
      },
      ecmaVersion: 'latest',
      parserOptions: {
        ecmaFeatures: {
          jsx: true,
        },
      },
    },
    settings: {
      'import/resolver': {
        typescript: true,
        node: true,
      },
    },
    linterOptions: {
      noInlineConfig: false,
    },
    plugins: {
      prettier,
      js,
      '@typescript-eslint': require('@typescript-eslint/eslint-plugin'),
      import: importPlugin,
      react,
      'jsx-a11y': jsxA11y,
      'react-hooks': reactHooks,
    },
    rules: {
      ...js.configs.recommended.rules,
      ...ts.configs.recommended.rules,
      ...importPlugin.flatConfigs.recommended.rules,
      ...react.configs.recommended.rules,
      ...reactHooks.configs.recommended.rules,
      ...jsxA11y.configs.recommended.rules,
      // prettier
      'prettier/prettier': 'error',
      // js rules
      'no-console': 'error',
      'no-unused-vars': 'off',
      // typescript rules
      '@typescript-eslint/no-unused-vars': [
        'error',
        {
          varsIgnorePattern: '^_',
        },
      ],
      '@typescript-eslint/no-explicit-any': 'error',
      // import rules
      'import/order': [
        'error',
        {
          groups: ['builtin', 'external', 'internal', 'type', 'sibling', 'parent', 'index'],
          'newlines-between': 'always',
          warnOnUnassignedImports: true,
          sortTypesGroup: true,
        },
      ],
      // react rules
      'react/react-in-jsx-scope': 'off',
      'react/no-unknown-property': ['error', { ignore: ['styleName'] }],
    },
  },
]);
