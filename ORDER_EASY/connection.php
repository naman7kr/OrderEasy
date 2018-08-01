<?php

$server_conn="localhost";
$username_conn="root";
$password_conn="";
$db_conn="order_easy";

$conn=mysqli_connect($server_conn, $username_conn, $password_conn, $db_conn);

if(!$conn){
	die( "connection failed:".mysqli_connect_error() );
}
//echo "connection established" ;

/*$password=password_hash("password",PASSWORD_DEFAULT);
echo $password;*/

?>