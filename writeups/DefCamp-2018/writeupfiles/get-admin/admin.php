<?php

if(intval($_SESSION['userid']) <= 0) {
    header("Location: /index.php");
}
?><!DOCTYPE html>
<html>
<head>
    <title>Dashboard</title>
    <link rel="stylesheet" href="/style.css?" type="text/css" />
</head>
<body>

<form method="post" action="/">
    <div class="text-center" style="padding:50px 0">
        <div class="logo">Admin</div>
        <!-- Main Form -->
        <div class="">
            <?php
 

            if($_SESSION['userid'] === "1") {
                echo FLAG;
            } else {
                echo 'Try harder.';
            }
            ?>

            <div><a href="profile.php">Profile</a> | <a href="logout.php">Logout</a></div>  
        </div>
        <!-- end:Main Form -->
    </div>
</form>
</body>
</html>
