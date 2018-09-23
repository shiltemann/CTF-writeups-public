<?php
include_once('config.php');
?>
<!DOCTYPE html>
<html>
<head>
    <title>Profile</title>
    <link rel="stylesheet" href="/style.css?" type="text/css" />
</head>
<body>

<form method="post" action="register.php">
    <div class="text-center" style="padding:50px 0">
        <div class="logo">Register</div>
        <div>
        <?php 

        if(isset($_POST['username'],$_POST['password'],$_POST['confirm_password'],$_POST['email'])) {
            if($_POST['password'] !== $_POST['confirm_password']) {
                die("invalid password");
            }

            $auth = new AuthLib($db);
            $response =  $auth->createUser($_POST['username'],$_POST['password'], $_POST['email']);
            if($response !== true) {
                echo $response;
            } else {
                echo 'Successfully registered';
            }
        }
        ?></div>
        <!-- Main Form -->
        <div class="login-form-1">
            <form id="login-form" class="text-left">
                
                <div class="main-login-form">
                    <div class="login-group">
                        <div class="form-group">
                            <label for="username" class="sr-only">Username</label>
                            <input type="text" class="form-control" id="username" name="username" placeholder="test" >
                        </div>
                        <div class="form-group">
                            <label for="password" class="sr-only">Password</label>
                            <input type="password" class="form-control" id="password" name="password" placeholder="test">
                        </div>
                        <div class="form-group">
                            <label for="confirm_password" class="sr-only">Confirm Password</label>
                            <input type="password" class="form-control" id="confirm_password" name="confirm_password" placeholder="test">
                        </div>
                        <div class="form-group">
                            <label for="email" class="sr-only">Email</label>
                            <input type="email" class="form-control" id="email" name="email" placeholder="test@test.com" >
                        </div>
                    </div>
                    <button type="submit" class="login-button"><i class="fa fa-chevron-right"></i></button>
                </div>
            </form>
        </div>
        <!-- end:Main Form -->
    </div>
</form>
</body>
</html>