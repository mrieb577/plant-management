import axios from "axios";
import { useState } from "react";

export default function LoginForm(){
  const [message, setMessage] = useState("");
  const [token, setToken] = useState("");

  function handleSubmit(event : any){
    event.preventDefault();
    console.log("Submitting login form");

    axios.post("http://localhost:8080/account/generate-token",
      {
        username: event.target.username.value,
        password: event.target.password.value
      },
      {
        headers: {
          'Content-Type': 'application/json',
        }
      }
    ).then((response) => {
      console.log(response);
      if(response.data.code == 200){
        localStorage.setItem('token', response.data.body);
        setToken(response.data.body);
      } else {
        setMessage("Email or password do not match an existing account. Did you mean to sign up?");
      }
    }).catch((err) => {setMessage(err.message)});
  }

  function logout(){
    setToken('');
    localStorage.setItem('token', '');
  }

  return (<div>
    <p>{message}</p>
    { token ?
        (<div>
          <p>Current token is {token}</p>
          <button onClick={logout}>Log out</button>
        </div>)
      :
      (<form onSubmit={handleSubmit}>
        <label> Email: <input type="text" name="username" /> </label> <br/>
        <label> Password: <input type="password" name="password" /> </label> <br/>
        <input type="submit" value="Submit" />
      </form>)
    }
  </div>)
}
