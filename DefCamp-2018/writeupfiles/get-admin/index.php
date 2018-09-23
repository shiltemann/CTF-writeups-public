<?php 

include_once('config.php');

if (!isset($_SESSION['userid'])) {
    if(!empty($_COOKIE['user'])) {
        $u = decryptCookie($_COOKIE['user']);

        if($u['id'] > 0) {
            $_SESSION['userid'] = $u['id'];
            header("Location: /admin.php");
            exit;
        } 
        die('Invalid cookie.');
    } else if(isset($_POST['username'], $_POST['password'])) {
        $auth = new AuthLib($db);
        $userid = (int) $auth->authenticate($_POST['username'], $_POST['password']);
        if ($userid) {
            $q = $db->query('SELECT * FROM `users` where id='.$userid);
            $row = $q->fetch(\PDO::FETCH_ASSOC);

            $_SESSION['userid'] = $userid;
         
            setcookie('user',encryptCookie([
                'id' => $userid,
                'username' => $_POST['username'],
                'email' => $row['email'], 
            ]), time()+60*60*24*30);
            
            header("Location: /admin.php");
            exit;
        }
    }
    require_once('login.php');
} else {
    require_once('admin.php');
}