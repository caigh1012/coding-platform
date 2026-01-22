module.exports = {
  extends: 'stylelint-config-standard-scss',
  customSyntax: 'postcss-scss',
  plugins: ['stylelint-prettier', 'stylelint-scss'],
  rules: {
    'prettier/prettier': true,
  },
};
