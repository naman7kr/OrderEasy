<?php 
	
	require("connection.php");

	/* sends four food item of each type for home page*/

	if(isset($_POST['home'])){

		$array=array();

		$cnt=0;
		$user_id=$_POST['home'];
		//$user_id="mkj@gmail.com";

		$query="SELECT foodID FROM recommended WHERE userID='$user_id' AND foodID!=0 LIMIT 4";
		$result= mysqli_query($conn,$query);
		if(mysqli_num_rows($result)>0){

			while($row=mysqli_fetch_assoc($result)){
				
				$food_id=$row['foodID'];
				//echo $food_id." ";
				$query1="SELECT * FROM food_items WHERE id='$food_id'";
				$result1= mysqli_query($conn,$query1);

				$row1=mysqli_fetch_assoc($result1);

				$array["food_items"][$cnt]['id']=$row1['id'];
				$array["food_items"][$cnt]['name']=$row1['name'];
				$array["food_items"][$cnt]['image']=$row1['image'];
				$array["food_items"][$cnt]['price']=$row1['price'];
				$array["food_items"][$cnt]['category']=$row1['category'];
				$array["food_items"][$cnt]['description']=$row1['description'];
				$array["food_items"][$cnt]['quantity_type']=$row1['quantity_type'];
				$array["food_items"][$cnt]['item_type']=0;
				//echo $array[0][$cnt]['name'];
				$cnt=$cnt+1;

			}
		}
		
		for($i=1;$i<=5;$i++){

			$query="SELECT * FROM food_items WHERE item_type=$i LIMIT 4 ";
			$result= mysqli_query($conn,$query);
			if(mysqli_num_rows($result)>0){

				while($row=mysqli_fetch_assoc($result)){

					$array["food_items"][$cnt]['id']=$row['id'];
					$array["food_items"][$cnt]['name']=$row['name'];
					$array["food_items"][$cnt]['image']=$row['image'];
					$array["food_items"][$cnt]['price']=$row['price'];
					$array["food_items"][$cnt]['category']=$row['category'];
					$array["food_items"][$cnt]['description']=$row['description'];
					$array["food_items"][$cnt]['quantity_type']=$row['quantity_type'];
					$array["food_items"][$cnt]['item_type']=$row['item_type'];
					//echo $array[0][$cnt]['name'];
					$cnt=$cnt+1;

				}
			}

		}
		
		//print_r($array);
		$data=json_encode($array);
		echo $data;

	}

	/* sends all food items  */

	if(isset($_POST['all'])){

		$array=array();
		
		$cnt=0;
		$user_id=$_POST['all'];
		//$user_id="mkj@gmail.com";

		$query="SELECT foodID FROM recommended WHERE userID='$user_id' AND foodID!=0";
		$result= mysqli_query($conn,$query);
		if(mysqli_num_rows($result)>0){

			while($row=mysqli_fetch_assoc($result)){

				$food_id=$row['foodID'];
				$query1="SELECT * FROM food_items WHERE id='$food_id'";
				$result1= mysqli_query($conn,$query1);

				$row1=mysqli_fetch_assoc($result1);

				$array["food_items"][$cnt]['id']=$row1['id'];
				$array["food_items"][$cnt]['name']=$row1['name'];
				$array["food_items"][$cnt]['image']=$row1['image'];
				$array["food_items"][$cnt]['price']=$row1['price'];
				$array["food_items"][$cnt]['category']=$row1['category'];
				$array["food_items"][$cnt]['description']=$row1['description'];
				$array["food_items"][$cnt]['quantity_type']=$row1['quantity_type'];
				$array["food_items"][$cnt]['item_type']=0;
				//echo $array[0][$cnt]['name'];
				$cnt=$cnt+1;

			}
		}

		$query="SELECT * FROM food_items";
		$result= mysqli_query($conn,$query);
		
		if(mysqli_num_rows($result)>0){
			
			while($row=mysqli_fetch_assoc($result)){

				$array["food_items"][$cnt]['id']=$row['id'];
				$array["food_items"][$cnt]['name']=$row['name'];
				$array["food_items"][$cnt]['image']=$row['image'];
				$array["food_items"][$cnt]['price']=$row['price'];
				$array["food_items"][$cnt]['category']=$row['category'];
				$array["food_items"][$cnt]['description']=$row['description'];
				$array["food_items"][$cnt]['quantity_type']=$row['quantity_type'];
				$array["food_items"][$cnt]['item_type']=$row['item_type'];
				//echo $array[0][$cnt]['name'];
				$cnt=$cnt+1;

			}
		}		

		$data=json_encode($array);
		echo $data;

	}
	
	/*		waiter login	*/
	
	else if(isset($_POST['waiter_login'])) {
		
		$arr  = $_POST['waiter_login'];
		$data = json_decode($arr,true);
		$username = $data['username'];
		$password = $data['password'];
		
		$query = "SELECT name,password FROM waiter WHERE name='$username'";
		
		$result = mysqli_query($conn, $query);
		
		if(mysqli_num_rows($result) > 0 ) {
			
			$row = mysqli_fetch_assoc($result);
			
			$stored_password = $row['password'];
			
			if($password==$stored_password) {
				
				echo "1";	//login successful
			}
			
			else {
				
				echo "0";	// unsuccesful login
			}
		}
		
	}
	
	/*return table_no of waiter*/
	
	else if(isset($_POST['table_waiter'])){
		
		$array= array();
		
		$username=$_POST['table_waiter'];
		
		$query1="SELECT table_no FROM waiter WHERE username=$username";
		$result1=mysqli_query($conn,$query1);
		
		if(mysqli_num_rows($result1)>0){

			$row=mysqli_fetch_assoc($result1);
			
			$array['table_no'][0]=$row['table_no'];
			
		}
		
		$data=json_encode($array);
		echo $data;
		
	}
	
	/*	waiter assign part		*/
	
	
	else if(isset($_POST['qr_scan'])){

		$arr = $_POST['qr_scan'];
		$data = json_decode($arr,true);
		//$data=array('username'=>'abc@gmail.com','table_no'=>'3');
		
		$user = $data['username'];
		$table_no=$data['table_no'];
		$array=array();
		
		$query1="SELECT id FROM waiter WHERE table_no='$table_no'";
		$result1=mysqli_query($conn,$query1);

		if(mysqli_num_rows($result1)>0){		//waiter assigned hai pehle se
			//echo "waiter";
			$array['status'] = 1;
			$array['OTP'] = "";
		}
		else {
			$query="SELECT id FROM waiter ORDER BY table_no limit 1";
			$result= mysqli_query($conn,$query);
			$row=mysqli_fetch_assoc($result);
			
			$id = $row['id'];
			$query="UPDATE waiter set table_no=$table_no where id=$id";
			$result= mysqli_query($conn,$query);
			
			$otp = mt_rand(100000,999999);
			$table_no_int=(int)$table_no;
			$query4 = "INSERT INTO tables VALUES($table_no_int, '$user', '$id', $otp,0)";
			$result4 = mysqli_query($conn, $query4);
			
			$array['status']= -1;
			$array['OTP'] = $otp;
		}

		// Assigning OTP for new users & returning NULL for existing user
		
		$json = json_encode($array);
		echo $json;
	}

	/* details of the food_items assigned to a waiter */

	else if(isset($_POST['waiter_details'])){

		//$table_no=32;
		$table_no=$_POST['waiter_details'];
		$array= array();

		$query="SELECT * FROM store_order WHERE table_no=$table_no";
		$result=mysqli_query($conn,$query);

		$cnt=0;

		if(mysqli_num_rows($result)>0){

			while($row=mysqli_fetch_assoc($result)){

				$array["food_items"][$cnt]['food_name']=$row['food_name'];
				$array["food_items"][$cnt]['table_no']=$row['table_no'];
				$cnt=$cnt+1;

			}
		}

		$data=json_encode($array);
		echo $data;

	}
	
	
	/*	waiter remove hoga 	*/
	
	
	else if(isset($_POST['waiter_remove'])){
		//$id=1;
		$id=$_POST['waiter_remove'];

		$query="SELECT table_no FROM waiter where id=$id";
		$result= mysqli_query($conn,$query);

		$row=mysqli_fetch_assoc($result);
		$table_no=$row['table_no'];
		
		$query = "SELECT food_name FROM store_order WHERE table_no=$table_no";
		$result = mysqli_query($conn, $query);
		
		$food = '';
		while($row=mysqli_fetch_assoc($result)) {
			$arr1 = explode("-", $row['food_name']);
			$food = $food . $arr1[0] . ',';
		}
		//echo $food;
		$query = "SELECT users FROM tables WHERE table_no=$table_no";
		$result = mysqli_query($conn, $query);
		$row=mysqli_fetch_assoc($result);
		
		$arr = explode(",", $row['users']);
		
		foreach($arr as $name) {
			//echo $name." ";
			
			$query = "UPDATE user SET food_items = '$food' WHERE email = '$name'";
			$result = mysqli_query($conn, $query);
		}

		$query="UPDATE waiter set table_no='0' where id=$id";
		$result= mysqli_query($conn,$query);


		$query="DELETE FROM store_order where table_no='$table_no'";
		$result= mysqli_query($conn,$query);
		
		$query="DELETE FROM tables where table_no=$table_no";
		$result= mysqli_query($conn,$query);
		
		//$data=json_encode($array);
		//echo $data;
	}
	
	/*    return food item of particular user   */
	
	else if(isset($_POST['check_item'])) {
		$email = $_POST['check_item'];
		
		$query = "SELECT food_items FROM user WHERE email='$email'";
		$result = mysqli_query($conn, $query);
		$row=mysqli_fetch_assoc($result);
		
		echo $row['food_items'];
	}

	/*	new user entry in user table & set rating of all items of that user in review table to 0.0  */
	
	else if(isset($_POST['user_entry'])){

		$json=$_POST['user_entry'];

		//$filename = "../test json file/order_test.json";

		//$json = file_get_contents($filename);

		$data=json_decode($json,true);
		
		$user_name=$data['username'];
		$email=$data['email'];
		$img=$data['img'];
		
		//echo $user_name." ".$email;

		if($user_name&&$email){

			$query="SELECT email FROM user WHERE email='$email'";
			$result= mysqli_query($conn,$query);
			
			if(mysqli_num_rows($result)==0){

				$query="INSERT INTO user (id,user_name, email, user_image) VALUES (NULL,'$user_name','$email','$img')";
				$result= mysqli_query($conn,$query);
				
				$query1 = "SELECT id FROM food_items";
				$result1 = mysqli_query($conn,$query1);
				
				if(mysqli_num_rows($result1)>0){
					
					while($row1 = mysqli_fetch_assoc($result1)){
						
						$food_id = $row1['id'];
						$query3 ="INSERT INTO review (id,email,food_id,rating) VALUES(NULL,'$email','$food_id',0.0)";
						$result2 = mysqli_query($conn,$query3);
					}
				}
			}
		}
	}	

	
	/*store the review given by user*/

	else if(isset($_POST['review_entry'])){

		$json=$_POST['review_entry'];

		//$filename = "test.json";

		//$json = file_get_contents($filename);

		$data=json_decode($json,true);

		for($i=0;$i<sizeof($data);$i++){

			$email=$data[$i]['email'];
			$food_id=$data[$i]['food_id'];
			$rating=$data[$i]['rating'];

			if($email&&$food_id&&$rating){
				//echo $email." ".$food_id." ".$rating;
				
				$query="UPDATE review set rating='$rating' where email='$email' AND food_id='$food_id'";
				$result= mysqli_query($conn,$query);
				
			}
		}
	}

	else if(isset($_POST['store_order'])){

		$json=$_POST['store_order'];

		/*$test=array();

		$test[0]['food_id']=14;
		 $test[0]['qty']=1;
		 $test[0]['table_no']=32;
		
		 $test[1]['food_id']=20;
		 $test[1]['qty']=1;
		 $test[1]['table_no']=32;

		$json=json_encode($test);*/

		$data=json_decode($json,true);

		for($i=0;$i<sizeof($data);$i++){
			
			$food_id=$data[$i]['food_id'];
			$qty = $data[$i]['qty'];
			$table_no = $data[$i]['table_no'];
			
			$query="SELECT name FROM food_items WHERE id=$food_id";
			$result=mysqli_query($conn,$query);

			$row=mysqli_fetch_assoc($result);
			$name=$row['name'];
			
			$food=$name." -> ".$qty;
			$status=-1;
			//echo $food_id." ".$qty." ".$table_no." ".$food;

			$query1 = "INSERT INTO store_order (id,food_name,table_no,status) VALUES(NULL,'$food','$table_no','$status')";
			$result1 = mysqli_query($conn,$query1);

		}
		

		//echo $food;

		

	}

	/*send order to cook*/

	if(isset($_POST["cook_order"])){


		$array= array();

		$query="SELECT * FROM store_order WHERE flag=0";
		$result=mysqli_query($conn,$query);

		$cnt=0;

		if(mysqli_num_rows($result)>0){

			while($row=mysqli_fetch_assoc($result)){

				$array["food_items"][$cnt]['food_name']=$row['food_name'];
				$array["food_items"][$cnt]['table_no']=$row['table_no'];
				$cnt=$cnt+1;

			}
		}

		$query="UPDATE store_order SET flag=1 WHERE flag=0";
		$result=mysqli_query($conn,$query);

		$data=json_encode($array);
		echo $data;

	}

	else if(isset($_POST["get_table"])){

		$waiter_name=$_POST["get_table"];
		//$waiter_name="chotu";
		$query="SELECT * FROM waiter WHERE name='$waiter_name'";
		$result=mysqli_query($conn,$query);
		$cnt=0;

		if(mysqli_num_rows($result)>0){

			while($row=mysqli_fetch_assoc($result)){

				$array["waiter"][$cnt]['id']=$row['id'];
				$array["waiter"][$cnt]['table_no']=$row['table_no'];
				$cnt=$cnt+1;

			}
		}

		$data=json_encode($array);
		echo $data;

	}
	
	else if(isset($_POST['store_rating'])) {
		
		$json=$_POST['store_rating'];
		$data=json_decode($json,true);
		
		/*$data = array(
				"email" => "naman7kr@gmail.com",
				"food" => array(
						array("food_name" => "Stir Fried Chilli Chicken", "food_rat" => 5.0),
						array("food_name" => "Paya Shorba", "food_rat" => 4.0),
				)
		);*/
		
		$email = $data['email'];
		$query="UPDATE user SET food_items='' WHERE email='$email'";
		$result=mysqli_query($conn,$query);
		
		$len = sizeof($data['food']);
		for($i=0;$i<$len;$i++) {
			$name = $data['food'][$i]['food_name'];
			$rating = $data['food'][$i]['food_rat'];
			
			$query="SELECT id FROM food_items WHERE name='$name'";
			$result=mysqli_query($conn,$query);
			$row=mysqli_fetch_assoc($result);
			
			$food_id = $row['id'];
			
			$query = "UPDATE review SET rating=$rating WHERE food_id=$food_id";
			$result=mysqli_query($conn,$query);
		}
	}
	
	else if(isset($_POST['table_all'])) {
		
		$table_no = $_POST['table_all'];
		//$table_no = '1';
		$query = "SELECT * FROM tables WHERE table_no = '$table_no'";
		$result = mysqli_query($conn, $query);
		$row = mysqli_fetch_assoc($result);
		$id = (int)$row['waiters'];
		$emails=$row['users'];
		
		$query = "SELECT * FROM waiter WHERE id=$id";
		$result = mysqli_query($conn, $query);
		$row = mysqli_fetch_assoc($result);
		
		$contact = $row['contact_no'];
		$waiter_name=$row['name'];
		
		$array = array();
		$array['waiters_info'] = array(
										"name" => $waiter_name,
										"contact_no" => $contact
		);
		
		$users = explode(",", $emails);
		$i=0;
		
		foreach($users as $u) {
			$array['users_info'][$i] = array(
											"name" => $u
			);
			$i++;
		}
		$arr = json_encode($array);
		echo $arr;
	}
	
	else if(isset($_POST['prev_total'])) {
		$table_no = $_POST['prev_total'];
		//$table_no = 1;
		$query = "SELECT food_name FROM store_order WHERE table_no = $table_no";
		$result = mysqli_query($conn, $query);
		$total_price=0;
		while($row = mysqli_fetch_assoc($result)) {
			$food = $row['food_name'];
			$arr = explode("->",$food);
			$name = $arr[0];
			$qty = intval($arr[1]);
			$query1 = "SELECT price FROM food_items WHERE name = '$name'";
			$result1 = mysqli_query($conn, $query1);
			$row1 = mysqli_fetch_assoc($result1);
			$price = $row1['price'];
			$total_price = $total_price + ($qty*$price);
		}
		echo $total_price;
	}
	
	else if(isset($_POST['get_bill'])) {
		$table_no = $_POST['get_bill'];
		//$table_no = 1;
		$query = "SELECT food_name FROM store_order WHERE table_no = $table_no";
		$result = mysqli_query($conn, $query);
		$data=array();
		$i=0;
		while($row = mysqli_fetch_assoc($result)) {
			$food = $row['food_name'];
			$arr = explode("->",$food);
			$name = $arr[0];
			$qty = (int)$arr[1];
			$query1 = "SELECT price FROM food_items WHERE name = '$name'";
			$result1 = mysqli_query($conn, $query1);
			$row1 = mysqli_fetch_assoc($result1);
			$price = $row1['price'];
			$data['bill'][$i]['food_name'] = $name;
			$data['bill'][$i]['qty'] = $qty;
			$data['bill'][$i]['price'] = $price;
			$i++;
		}
		$json = json_encode($data);
		echo $json;
	}
	
	else if(isset($_POST['verify_otp'])) {
		$arr = json_decode($_POST['verify_otp'], true);
		$otp = $arr['otp'];
		$user = $arr['email'];
		$query = "SELECT OTP FROM tables WHERE OTP=$otp";
		$result = mysqli_query($conn, $query);
		
		if(mysqli_num_rows($result) > 0) {
			$query = "SELECT users,table_no FROM tables WHERE OTP=$otp";
			$result = mysqli_query($conn, $query);
			
			$row = mysqli_fetch_assoc($result);
			$add_users = $row['users'] . "," . $user;
			//echo $add_users;
			
			$query3 = "UPDATE tables SET users = '$add_users' WHERE OTP = $otp";
			$result3 = mysqli_query($conn, $query3);
			
			echo $row['table_no'];
		}
		else {
			echo 0;
		}
	}
	mysqli_close($conn);

?>