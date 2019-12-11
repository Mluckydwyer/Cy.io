

export function userInfo(){
        
        const API = 'http://coms-309-nv-4.misc.iastate.edu:8080/user/me';
        fetch(API, {
            method: 'get',
            headers: {
                'Authorization': 'Bearer ' + JSON.parse(localStorage.getItem("token")).accessToken,
            },
            
        }).then(response => {
            if (response.ok) {
                return response.json();
            }
            else {
                throw new Error('Something went wrong...');
            }
        }).then(data => {
            localStorage.setItem("info", JSON.stringify(data));
            return data;
        }).then(console.log);
        
        
    }
