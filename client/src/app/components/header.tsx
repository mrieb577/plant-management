import { Link } from "react-router-dom";
import "./header.css"

export default function Header(){
  return(
    <div>
      <nav>
        <Link to="/">Home</Link> | {" "}
        <Link to="/">My Plants</Link> | {" "}
        <Link to="/search">Search</Link> | {" "}
        <Link to="/">Log In</Link>
        <div className="headerUserInfo">
          <p>Morgan R.</p>
        </div>
      </nav>

    </div>
  );
}
