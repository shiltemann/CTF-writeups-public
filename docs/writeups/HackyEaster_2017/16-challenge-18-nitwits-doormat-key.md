---
layout: writeup
title: 'Nitwit’s doormat key'
level:
difficulty:
points:
categories: []
tags: []
flag:
---
## Challenge

## Solution

we check the source

    <!DOCTYPE html>
    <html>
    	<head>
    		<title>Awesome login by me!</title>
    		<meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1">
    		<script src="script.js" type="text/javascript"></script>
    		<link rel="stylesheet" href="style.css" type="text/css"/>
    	</head>
    	<body>
        <div id="container1">
      		<div class="form">
              <input id="uzr" type="text" placeholder="username"/>
              <input id="puzzwerd" placeholder="password" type="password"/>
              <input id="sub" type="button" value="Login!">
            </div>
        </div>
        <div id="container2">
    	 <img id="egg" src="../images/egg_gray.png">
        </div>
    	<script>
        v06b9e817c4ddcf60fbd82113f8c1f49b = [function(va587d4b34c724ca63a1afcbaeca7f254) {
        return '1407c2b75f43d3691c240e28204533da74ee4054f5f2538e87b69ec590b04de2c2bd190b';
    }, function(va587d4b34c724ca63a1afcbaeca7f254) {
        return v16441ef9304a5520d663e6580bf16679.createElement(va587d4b34c724ca63a1afcbaeca7f254);
    }, function(va587d4b34c724ca63a1afcbaeca7f254) {
        return va587d4b34c724ca63a1afcbaeca7f254[0].getContext(va587d4b34c724ca63a1afcbaeca7f254[1]);
    }, function(va587d4b34c724ca63a1afcbaeca7f254) {
        return va587d4b34c724ca63a1afcbaeca7f254[0].text = va587d4b34c724ca63a1afcbaeca7f254[1];
    }, function(va587d4b34c724ca63a1afcbaeca7f254) {
        return null;
    }, function(va587d4b34c724ca63a1afcbaeca7f254) {
        '6d363479c97439b921ad2bcba054992d8eda9a0c971df8f80920b84ab4eeae83cae6b76c';
    }, function(va587d4b34c724ca63a1afcbaeca7f254) {
        return 'e77a763321d6cf825534ab228e1dfa33e71447c1637dc3b97dcf2d564e2003f67e7c26aa';
    }, function(va587d4b34c724ca63a1afcbaeca7f254) {
        va587d4b34c724ca63a1afcbaeca7f254.style.display = 'none';
        return va587d4b34c724ca63a1afcbaeca7f254;
    }, function(va587d4b34c724ca63a1afcbaeca7f254) {
        v159851e0e855ec9a87293bada9f49593.onload = va587d4b34c724ca63a1afcbaeca7f254
    }, function(va587d4b34c724ca63a1afcbaeca7f254) {
        v159851e0e855ec9a87293bada9f49593.src = va587d4b34c724ca63a1afcbaeca7f254;
    }, new Function("va587d4b34c724ca63a1afcbaeca7f254", "return unescape(decodeURIComponent(window.atob(va587d4b34c724ca63a1afcbaeca7f254)))"), function(va587d4b34c724ca63a1afcbaeca7f254) {
        vbe3ae157bcaf01bd49ec5a9b228e92fb = new Function('va587d4b34c724ca63a1afcbaeca7f254', v06b9e817c4ddcf60fbd82113f8c1f49b[10](v21a3d2d3fc5c2c1e1f3a633bd8f16f7e[va587d4b34c724ca63a1afcbaeca7f254]));
        return vbe3ae157bcaf01bd49ec5a9b228e92fb;
    }];
    v1341746e3d58a8c0d7ce43b877b6beb1 = [0, 255, 0];
    v21a3d2d3fc5c2c1e1f3a633bd8f16f7e = ['cmV0dXJuJTIwJ2NhbnZhcyclM0I=', 'cmV0dXJuJTIwJ25vbmUnJTNC', 'cmV0dXJuJTIwJzJkJyUzQg==', 'cmV0dXJuJTIwJ3NjcmlwdCclM0I=', '', 'vd547050f5153d6953e12ea54b398855a', 'v6b31cf3f4c1305942d60342286cd09c5', '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', 'cmV0dXJuJTIwZG9jdW1lbnQ=', 'Zm9yKHZiOTMyYjI4NGE0YTdlYTdjZWJlMzc5ZTk3MzlhNjYwZSUzRHYxMzQxNzQ2ZTNkNThhOGMwZDdjZTQzYjg3N2I2YmViMSU1QjIlNUQlM0IlMjB2YjkzMmIyODRhNGE3ZWE3Y2ViZTM3OWU5NzM5YTY2MGUlMjAlM0MlMjB2NGM5NWEyNTliYTJmOWQyZGMwMTBkYzEwMzc5Y2FlZjIuZGF0YS5sZW5ndGglM0IlMjB2YjkzMmIyODRhNGE3ZWE3Y2ViZTM3OWU5NzM5YTY2MGUlMkIlM0Q0KXZhNjI3YjZjOGVlMTAzNDk0NGE4Njc5ODNjNTBlNDlhYSUyQiUzRCh2NGM5NWEyNTliYTJmOWQyZGMwMTBkYzEwMzc5Y2FlZjIuZGF0YSU1QnZiOTMyYjI4NGE0YTdlYTdjZWJlMzc5ZTk3MzlhNjYwZSU1RCElM0R2MTM0MTc0NmUzZDU4YThjMGQ3Y2U0M2I4NzdiNmJlYjElNUIxJTVEKSUzRnY5OGI5NjM0NGQxNzMxNzFlNzAzNzY1ODMzOTI0M2EyYSh2NGM5NWEyNTliYTJmOWQyZGMwMTBkYzEwMzc5Y2FlZjIuZGF0YSU1QnZiOTMyYjI4NGE0YTdlYTdjZWJlMzc5ZTk3MzlhNjYwZSU1RCklM0F2MjFhM2QyZDNmYzVjMmMxZTFmM2E2MzNiZDhmMTZmN2UlNUI0JTVEJTNCJTIwdmE2MjdiNmM4ZWUxMDM0OTQ0YTg2Nzk4M2M1MGU0OWFhJTNEdmE2MjdiNmM4ZWUxMDM0OTQ0YTg2Nzk4M2M1MGU0OWFhLnRyaW0oKSUzQg==', 'cmV0dXJuJTIwbmV3JTIwSW1hZ2UoKSUzQg==', 'cmV0dXJuJTIwU3RyaW5nLmZyb21DaGFyQ29kZSh2YTU4N2Q0YjM0YzcyNGNhNjNhMWFmY2JhZWNhN2YyNTQpJTNC'];
    v16441ef9304a5520d663e6580bf16679 = v06b9e817c4ddcf60fbd82113f8c1f49b[11](11)();
    v62647b9bbd4174f7dfe26356c2d80254 = new Function('va587d4b34c724ca63a1afcbaeca7f254', v06b9e817c4ddcf60fbd82113f8c1f49b[10](v21a3d2d3fc5c2c1e1f3a633bd8f16f7e[10]));
    v159851e0e855ec9a87293bada9f49593 = v06b9e817c4ddcf60fbd82113f8c1f49b[7](v06b9e817c4ddcf60fbd82113f8c1f49b[11](13)());
    v06b9e817c4ddcf60fbd82113f8c1f49b[8](function() {
        vf47cdd4c73ada3018bb2235586095298 = v62647b9bbd4174f7dfe26356c2d80254(v21a3d2d3fc5c2c1e1f3a633bd8f16f7e[5]);
        v14b0e537ea8314aa8eff15a14130f2dc = v06b9e817c4ddcf60fbd82113f8c1f49b[1](v06b9e817c4ddcf60fbd82113f8c1f49b[11](0)());
        v14b0e537ea8314aa8eff15a14130f2dc.width = v159851e0e855ec9a87293bada9f49593.width;
        v14b0e537ea8314aa8eff15a14130f2dc.height = v159851e0e855ec9a87293bada9f49593.height;
        v14b0e537ea8314aa8eff15a14130f2dc.style.display = v06b9e817c4ddcf60fbd82113f8c1f49b[11](1)();
        va627b6c8ee1034944a867983c50e49aa = v21a3d2d3fc5c2c1e1f3a633bd8f16f7e[4];
        v22f584e55a18700bb1e819235e9faec2 = v06b9e817c4ddcf60fbd82113f8c1f49b[2]([v14b0e537ea8314aa8eff15a14130f2dc, v06b9e817c4ddcf60fbd82113f8c1f49b[11](2)()]);
        v98b96344d173171e7037658339243a2a = new Function('va587d4b34c724ca63a1afcbaeca7f254', v06b9e817c4ddcf60fbd82113f8c1f49b[10](v21a3d2d3fc5c2c1e1f3a633bd8f16f7e[14]));
        v22f584e55a18700bb1e819235e9faec2.drawImage(v159851e0e855ec9a87293bada9f49593, v1341746e3d58a8c0d7ce43b877b6beb1[0], v1341746e3d58a8c0d7ce43b877b6beb1[0]);
        v4c95a259ba2f9d2dc010dc10379caef2 = v22f584e55a18700bb1e819235e9faec2.getImageData(v1341746e3d58a8c0d7ce43b877b6beb1[0], v1341746e3d58a8c0d7ce43b877b6beb1[0], v14b0e537ea8314aa8eff15a14130f2dc.width, v14b0e537ea8314aa8eff15a14130f2dc.height);
        v312f239ab11ab792bbc638880cef0016 = v06b9e817c4ddcf60fbd82113f8c1f49b[11](12)();
        (new Function(v06b9e817c4ddcf60fbd82113f8c1f49b[10](va627b6c8ee1034944a867983c50e49aa)))();
        vd547050f5153d6953e12ea54b398855a = v06b9e817c4ddcf60fbd82113f8c1f49b[4](v22f584e55a18700bb1e819235e9faec2);
        v159851e0e855ec9a87293bada9f49593 = v06b9e817c4ddcf60fbd82113f8c1f49b[4](vd547050f5153d6953e12ea54b398855a);
        v14b0e537ea8314aa8eff15a14130f2dc = v06b9e817c4ddcf60fbd82113f8c1f49b[4](v14b0e537ea8314aa8eff15a14130f2dc);
        v22f584e55a18700bb1e819235e9faec2 = v06b9e817c4ddcf60fbd82113f8c1f49b[4](v4c95a259ba2f9d2dc010dc10379caef2);
        v4c95a259ba2f9d2dc010dc10379caef2 = v06b9e817c4ddcf60fbd82113f8c1f49b[4](v22f584e55a18700bb1e819235e9faec2);
        vb932b284a4a7ea7cebe379e9739a660e = v06b9e817c4ddcf60fbd82113f8c1f49b[4](v22f584e55a18700bb1e819235e9faec2);
        va627b6c8ee1034944a867983c50e49aa = v06b9e817c4ddcf60fbd82113f8c1f49b[4](v22f584e55a18700bb1e819235e9faec2);
        v0acc8db6e0b9f93a7f5e1fba4e39431f = v06b9e817c4ddcf60fbd82113f8c1f49b[4](v22f584e55a18700bb1e819235e9faec2);
        ve7773f4e18e20899677477e2e500daef = v06b9e817c4ddcf60fbd82113f8c1f49b[4](v22f584e55a18700bb1e819235e9faec2);
        vd547050f5153d6953e12ea54b398855a = v06b9e817c4ddcf60fbd82113f8c1f49b[4](v22f584e55a18700bb1e819235e9faec2);
        v1362f0e27add34a3855900a0346601e2 = v06b9e817c4ddcf60fbd82113f8c1f49b[4](v22f584e55a18700bb1e819235e9faec2);
        v16441ef9304a5520d663e6580bf16679 = v06b9e817c4ddcf60fbd82113f8c1f49b[4](v22f584e55a18700bb1e819235e9faec2);
        v312f239ab11ab792bbc638880cef0016 = v06b9e817c4ddcf60fbd82113f8c1f49b[4](v22f584e55a18700bb1e819235e9faec2);
        v21a3d2d3fc5c2c1e1f3a633bd8f16f7e = v06b9e817c4ddcf60fbd82113f8c1f49b[4](v22f584e55a18700bb1e819235e9faec2);
        v1341746e3d58a8c0d7ce43b877b6beb1 = v06b9e817c4ddcf60fbd82113f8c1f49b[4](v22f584e55a18700bb1e819235e9faec2);
        va587d4b34c724ca63a1afcbaeca7f254 = v06b9e817c4ddcf60fbd82113f8c1f49b[4](v22f584e55a18700bb1e819235e9faec2);
        va587d4b34c724ca63a1afcbaeca7f254 = v06b9e817c4ddcf60fbd82113f8c1f49b[4](vf47cdd4c73ada3018bb2235586095298);
        v06b9e817c4ddcf60fbd82113f8c1f49b = v06b9e817c4ddcf60fbd82113f8c1f49b[4](v22f584e55a18700bb1e819235e9faec2);
    });
    v312f239ab11ab792bbc638880cef0016 = v06b9e817c4ddcf60fbd82113f8c1f49b[9](v06b9e817c4ddcf60fbd82113f8c1f49b[11](7)() + v21a3d2d3fc5c2c1e1f3a633bd8f16f7e[9]);

    	</script>
    	</body>
    </html>
{: .language-html}

Let's try to deobfuscate a bit:

    my_function_array = [
        //0
        function(my_var) {
            return '1407c2b75f43d3691c240e28204533da74ee4054f5f2538e87b69ec590b04de2c2bd190b';
        },
        //1
        function(my_var) {
            return charlie.createElement(my_var);
        },
        //2
        function(my_var) {
            return my_var[0].getContext(my_var[1]);
        },
        //3
        function(my_var) {
            return my_var[0].text = my_var[1];
        },
        //4
        function(my_var) {
            return null;
        },
        //5
        function(my_var) {
            '6d363479c97439b921ad2bcba054992d8eda9a0c971df8f80920b84ab4eeae83cae6b76c';
        },
        //6
        function(my_var) {
            return 'e77a763321d6cf825534ab228e1dfa33e71447c1637dc3b97dcf2d564e2003f67e7c26aa';
        },
        //7
        function(my_var) {
            my_var.style.display = 'none';
            return my_var;
        },
        //8
        function(my_var) {
            foxtrot.onload = my_var
        },
        //9
        function(my_var) {
            foxtrot.src = my_var;
        },
        //10
        new Function("my_var",
            "return unescape(decodeURIComponent(window.atob(my_var)))"),

        //11
        function(my_var) {
            my_new_function = new Function('my_var',
                my_function_array[10]( my_data_array[my_var] ));
            return my_new_function;
        }
    ];

    my_list = [0, 255, 0];
    my_data_array = [
        "return 'canvas';",
        "return 'none';",
        "return '2d';",
        "return 'script';",
        '',
        'tango',
        'v6b31cf3f4c1305942d60342286cd09c5',
        '<png image, see writeupfiles/chall18.png>',
        'return document',
        '<just data? see writeupfiles/chall18_justdata>',
        'return new Image();',
        'return String.fromCharCode(my_var);'
    ];

    charlie = my_function_array[11](11)();
    hotel = new Function('my_var', my_function_array[10](my_data_array[10]));
    foxtrot = my_function_array[7](my_function_array[11](13)());
    my_function_array[8](function() {
        kilo = hotel(my_data_array[5]);
        bravo = my_function_array[1](my_function_array[11](0)());
        bravo.width = foxtrot.width;
        bravo.height = foxtrot.height;
        bravo.style.display = my_function_array[11](1)();

        lima = my_data_array[4];
        alpha = my_function_array[2]([bravo, my_function_array[11](2)()]);
        mike = new Function('my_var', my_function_array[10](my_data_array[14]));
        alpha.drawImage(foxtrot, my_list[0], my_list[0]);
        romeo = alpha.getImageData(my_list[0], my_list[0], bravo.width, bravo.height);
        sierra = my_function_array[11](12)();

        (new Function(my_function_array[10](lima)))();

        tango = my_function_array[4](alpha);
        foxtrot = my_function_array[4](tango);
        bravo = my_function_array[4](bravo);
        alpha = my_function_array[4](romeo);
        romeo = my_function_array[4](alpha);
        uniform = my_function_array[4](alpha);
        lima = my_function_array[4](alpha);

        victor = my_function_array[4](alpha);
        yankee = my_function_array[4](alpha);
        tango = my_function_array[4](alpha);
        zulu = my_function_array[4](alpha);
        charlie = my_function_array[4](alpha);
        sierra = my_function_array[4](alpha);
        my_data_array = my_function_array[4](alpha);
        my_list = my_function_array[4](alpha);
        my_var = my_function_array[4](alpha);
        my_var = my_function_array[4](kilo);
        my_function_array = my_function_array[4](alpha);
    });
    sierra = my_function_array[9](my_function_array[11](7)() + my_data_array[9]);
{: .language-javascript}

