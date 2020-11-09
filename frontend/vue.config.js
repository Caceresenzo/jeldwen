module.exports = {
	"transpileDependencies": [
		"vuetify"
	],
	devServer: {
		port: 8082,
		proxy: {
			'/api': {
				target: 'http://localhost:8000/',
				changeOrigin: true,
				//logLevel: 'debug',
				pathRewrite: { "^/api": "/" }
			},
		},
	}
}
