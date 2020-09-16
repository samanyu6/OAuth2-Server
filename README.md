# OAuth2-Server

This server is used to authenticate and authorize users using OAuth2.0 

### Endpoints

1. Get Token for username and password.
    #### Request
    ```Bash
        POST : /oauth/token
        
        Authorization values : {
            Username: web
            Password: webpass
        }
   
        Authentication values: {
            grant_type: password
            username: samanyu
            password: sampass
        }
    ```
   
   #### Response
   ```Bash
        {
            "access_token": "6c0c84d1-fca3-481a-aec9-ed1b87d91370",
            "token_type": "bearer",
            "expires_in": 42397,
            "scope": "READ WRITE"
        }
   ```
   
   Authorization values : used for authorizing the client apps.
   
   Authentication values: used for authenticating the client, via the client app.