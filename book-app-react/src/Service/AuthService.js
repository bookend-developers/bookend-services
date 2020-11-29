
class AuthService {
    login(username, password) {
        let myHeaders = new Headers();
        myHeaders.append("Authorization", "Basic bW9iaWxlOnBpbg==");

        let formdata = new FormData();
        formdata.append("grant_type", "password");
        formdata.append("username", username);
        formdata.append("password", password);

        let requestOptions = {
            method: 'POST',
            headers: myHeaders,
            body: formdata,
            redirect: 'follow'
        };

        return fetch("http://localhost:9191/oauth/token", requestOptions)
            .then(response => response.text())
            .then(result => {
                console.log(JSON.parse(result).username)
                if (JSON.parse(result).error !== "unauthorized" && !result.includes("error")) {
                    localStorage.setItem("user", result.slice(17,53));
                    localStorage.setItem("username", JSON.parse(result).username);

                }
                return result;
            })

    }


    handleUserName(token) {
        let myHeaders = new Headers();
        myHeaders.append("Authorization", "Basic bW9iaWxlOnBpbg==");

        let requestOptions = {
            method: 'GET',
            headers: myHeaders,
            redirect: 'follow'
        };

       return fetch("http://localhost:9191/oauth/check_token?token="+token, requestOptions)
            .then(response => response.text())
            .then(result => {
                if (JSON.parse(result).user_name!=="undefined") {
                    localStorage.setItem("userName", JSON.parse(result).user_name);
                }
                return result;
            })
    }

    handleUserId(userName){
        let requestOptions = {
            method: 'GET',
            redirect: 'follow'
        };

        return fetch("http://localhost:9191/api/profile/"+userName, requestOptions)
            .then(response => response.text())
            .then(result => {
                if (result.slice(10,23)!=="invalid_token") {
                    console.log(JSON.parse(result).id)
                    localStorage.setItem("userId", JSON.parse(result).id);
                }else{
                    this.props.history.push("/");
                    window.location.reload();
                }
                return result;
            })
    }

    logout() {
        localStorage.removeItem("user");
        localStorage.removeItem("username");
        localStorage.removeItem("userId");
    }

    getCurrentUserName() {
        console.log(localStorage.getItem('username'));
        return localStorage.getItem('username');

    }

    getCurrentUser() {
        return localStorage.getItem('user');
    }

    getCurrentUserId(){
        return localStorage.getItem('userId')
    }
}

export default new AuthService();