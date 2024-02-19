import axios from "axios";

const RemoveBookmark = async ({command, bookId, userId}) => {
    const token = sessionStorage.getItem('token');
    const requestOptions = {
        headers: {
            Authorization: token
        }
    }
    console.log("command", command)
    if (command) {
        try {

            const response = await axios.delete('http://localhost:8088/dionmangareader/bookmarks/remove?bookId=' + bookId + '&userId=' + userId, requestOptions);
            return response.data;
        } catch (error) {
            console.error('Error fetching data:', error);
        }
    } else {

        const requestBody = {
            bookId: bookId,
            userId: userId,
            currentChapter: 0,
        };
        try {
            const response = await axios.post('http://localhost:8088/dionmangareader/bookmarks', requestBody, requestOptions);
            return response.data;
        }catch (error) {
            console.error('Error fetching data:', error);
        }
    }
}
export default RemoveBookmark;