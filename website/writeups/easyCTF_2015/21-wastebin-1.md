---
layout: writeup
title: Wastebin 1
level: 
difficulty: 
points: 
categories: []
tags: []
flag: easyctf{cr4zy_p4ssw0rds}
---
## Challenge

I created a paste-sharing site the other day. Since client-side is
faster, I decided to retrieve the entire database and store it
client-side. No one should be able to see it, right? Prove me wrong by
finding the admin password. page

## Solution

again the solution is just in the source code

    [..]
    <script type="text/javascript">
    			var users = [
    				{ username: "admin", password: "easyctf{cr4zy_p4ssw0rds}" },
    				{ username: "tom", password: "easyctf{9et_r3kt}" },
    				{ username: "becky", password: "easyctf{w0w_so_s3cure}" }
    			];
    			document.getElementById("users_registered").innerHTML = users.length;
    			window.check_login = function() {
    				var username = document.getElementById("username").value;
    				var password = document.getElementById("password").value;
    				for(var user in users) {
    					if (users[user].username == username && users[user].password == password) {
    						alert("Access granted!");
    						return;
    					}
    				}
    				alert("Dang it, you screwed up.");
    			}
    		</script>
    [..]
{: .language-html}

