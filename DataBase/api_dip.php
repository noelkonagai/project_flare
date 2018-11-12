<?php 
require "init.php";


//********************************************** Login ****************************************************//

if(isset($_GET['login'])){

	if(isset($_GET['user_name']) && isset($_GET['password']) && isset($_GET['imei'])){
		
	
		$user_name   = $_GET['user_name'];
		$password     = $_GET['password'];
		$imei      = $_GET['imei'];
		
		$millis_time_stamp;
		
		
		$query_1 = "SELECT * FROM user_demo WHERE user_name like '$user_name' AND password like '$password' AND imei like '$imei'";
		$result=mysqli_query($con,$query_1);
		if(mysqli_num_rows($result)>0)
        {
			while($row = mysqli_fetch_assoc($result)) {
			
					$millis_time_stamp=$row["idle_time"];
			
			}
				


		$return = array('status'=>'success','message'=>'Welcome','idle_time'=>$millis_time_stamp);
		}else{
			$return = array('status'=>'error','message'=>'User doesn\'t exists');
		}
		
	
	}
	else{
		$return = array('status'=>'error','message'=>'Missing username or Password');
	}
	
}

//********************************************** Insert QR Code ****************************************************//

if(isset($_GET['insert_qr_code'])){
		$id=0;
			
			$qr_value1=$_POST['qr_value'];
			$location1=$_POST['location'];
			$date1=$_POST['date'];
			$time1=$_POST['time'];
			$ticket_image1=$_POST['ticket_image'];
			$hash_value1="";
			$timestamp1="";
			$signature_image1=$_POST['signature_image'];
			
			///////////////////////////////////////////////////////////////////////////////////////////
					$query_2 ="INSERT INTO qr_code VALUES('$id','$qr_value1','$location1')";
					$result=mysqli_query($con,$query_2);
					
			///////////////////////////////////////////////////////////////////////////////////////////			
					$query_3 ="INSERT INTO ticket VALUES('$id','$date1','$time1','$ticket_image1')";
					$result=mysqli_query($con,$query_3);
					
			///////////////////////////////////////////////////////////////////////////////////////////		   
			$query_4 ="INSERT INTO signature VALUES('$id','$hash_value1','$signature_image1',now())";
					$result=mysqli_query($con,$query_4);
			
			$return = array('status'=>'success','message'=>'Data Successfully Inserted');
			
	
}


echo json_encode($return);

//********************************************** Insert Ticket ****************************************************//


/*
if(isset($_GET['insert_ticket'])){
	if( isset($_GET['date']) && isset($_GET['time']) && isset($_GET['ticket_image'])){
			$col = 'date,time,ticket_image';
			$id=0;
			$date1=$_GET['date'];
			$time1=$_GET['time'];
			$ticket_image1=$_GET['ticket_image'];
			$val = "'".$id."','".$_GET['date']."','".$_GET['time']."','".$_GET['ticket_image']."'";
			
					$query_2 ="INSERT INTO qr_code VALUES('$id','$date1','$time1','$ticket_image1')";
					$result=mysqli_query($con,$query_2);
			
			$return = array('status'=>'success','message'=>'Data Successfully Inserted');
		
				
	}else{
		$return = array('status'=>'error','message'=>'Missing Required parameters');
	}
}
*/
//********************************************** Get All QR Code ****************************************************//
/*

if(isset($_GET['get_qr_code'])){
	
			
					$query = query("select * from qr_code");
					$data=array();
					while($row = fetch_assoc($query)){
							$data[] = $row;
						}
						if(!empty($data)){
							$return = array('status'=>'success','data'=>$data);
						}else{
							$return = array('status'=>'error','message'=>'data not found');
							}
	}
	*/
//********************************************** Get All tickets ****************************************************//
/*

if(isset($_GET['get_ticket'])){
	
			
					$query = query("select * from ticket");
					$data=array();
					while($row = fetch_assoc($query)){
							$data[] = $row;
						}
						if(!empty($data)){
							$return = array('status'=>'success','data'=>$data);
						}else{
							$return = array('status'=>'error','message'=>'data not found');
							}
	}
	*/

	
?>