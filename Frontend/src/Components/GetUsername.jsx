import axios from 'axios';

const GetUsername = async () => {
    const token = sessionStorage.getItem('token');
    const requestOptions = {
        headers: {
            Authorization: token
        }
    };
    const response = await axios.get('http://localhost:8088/dionmangareader/auth/username', requestOptions);
    return response.data;

};

export default GetUsername;
