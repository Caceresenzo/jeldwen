
import axios from 'axios'
import application from '../../application'

export default axios.create({
	baseURL: application.api.baseUrl
})