import axios from 'axios';

const GetUser = async (username) => {
    const token = sessionStorage.getItem('token');
    const requestOptions = {
        headers: {
            Authorization: token
        }
    };
    const response = await axios.get('http://localhost:8088/dionmangareader/users/?username='+username.toString(), requestOptions);
    return response.data;
};

export default GetUser;