import axios from "axios";
import { useState } from "react";

export default function LoginForm(){
  const [message, setMessage] = useState("");

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
      localStorage.setItem('token', response.data);
    }).catch((err) => {setMessage(err.message)});
  }

  return (<div>
    { localStorage.getItem('token') ?
        (<div>
          <p>Current token is {localStorage.getItem('token')}</p>
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
