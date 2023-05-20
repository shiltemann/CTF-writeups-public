/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javaapplication1

import groovy.transform.Field

def name='grinny'
int count = 0;
@Field
HashMap<String, boolean[]> poss = new HashMap<>();


poss.put("q", new boolean[10]);
poss.put("h", new boolean[10]);
poss.put("s", new boolean[10]);
poss.put("i", new boolean[10]);
poss.put("z", new boolean[10]);
poss.put("u", new boolean[10]);
poss.put("y", new boolean[10]);
poss.put("n", new boolean[10]);
poss.put("v", new boolean[10]);
poss.put("l", new boolean[10]);
poss.put("w", new boolean[10]);
poss.put("d", new boolean[10]);
poss.put("g", new boolean[10]);
poss.put("b", new boolean[10]);

boolean checkTriples(int ...nrs){
    List<Integer> s = new ArrayList<Integer>();
    s.addAll(nrs);
    Collections.sort(s);
    int last = -1;
    int doubles = 0;
    for(int i:s){
        if(i == last){
            doubles++;
            if(doubles == 2)
                return false;
        }else{
            doubles = 0;
            last=i;
        }
        
    }
    return true;
}

void checkImpossible(String ...keys){
    for(String key:keys){
        boolean[] p = poss.get(key);
        for(int i=0;i<p.length;i++){
            if(!p[i])
                print key+" cannot be a "+i+"\n";
        }
    }
    
}


int analyse(String[] names, int[] values, int depth, Closure cb){
    int count = 0;
    if(depth < names.length){
        for(int i=0;i<10;i++){
            values[depth] = i;
            count += analyse(names,values,depth+1,cb);
        }
    }else if(checkTriples(values) && cb(values)){
        int i=0;
        for(String n:names){
            poss.get(n)[values[i]] = true;
            i++;
        }
        return 1;
    }
    return 0;
}

def cb = { int[] v-> (v[0]+v[1]^v[2]+v[3]|v[1]) % 10 == v[4] };

print analyse( ["u", "y", "n", "v", "l"].toArray(new String[0]), new int[5], 0, cb);

for(int u=0;u<10;u++){
    for(int y=0;y<10;y++){
        for(int n=0;n<10;n++){
            for(int v=0;v<10;v++){
                for(int l=0;l<10;l++){
                    int x = u+y^n+v|y;
                    if(x%10 == l && checkTriples(u,y,n,v,l)){
                        poss.get("u")[u]=true;
                        poss.get("y")[y]=true;
                        poss.get("n")[n]=true;
                        poss.get("v")[v]=true;
                        poss.get("l")[l]=true;
                        count++;
                        //print ""+ count +": "+u+" "+y+" "+n+" "+v+" "+l+"\n";
                    }
                }
            }
        }
    }
}

checkImpossible("u","y","n","v","l");

print "=====================================================\n";
print "count: "+count+"\n";
count = 0;


for(int q=1;q<10;q++){
    for(int h=0;h<10;h++){
        for(int s=0;s<10;s++){
            //if(q == h && q == s)
            //    continue;
            for(int i=0;i<10;i++){
                for(int z=0;z<10;z++){
                    
                    int x = q^h-s^i+z;
                    if(x%10 == q && checkTriples(q,h,s,i,z)){
                        poss.get("q")[q]=true;
                        poss.get("h")[h]=true;
                        poss.get("s")[s]=true;
                        poss.get("i")[i]=true;
                        poss.get("z")[z]=true;
                        count++;
                        //print ""+ count +": "+q+" "+h+" "+s+" "+i+" "+z+"\n";
                    }
                }
            }
        }
    }
}
print "count: "+count+"\n";
count =0;
checkImpossible("q","h","s","i","z");

for(int w=0;w<10;w++){
    for(int u=0;u<10;u++){
        for(int h=0;h<10;h++){
            for(int d=0;d<10;d++){
                //if(q == h && q == s)
                //    continue;
                for(int g=0;g<10;g++){
                    for(int b=0;b<10;b++){

                        int x = w-u&h|d^g;
                        if(x%10 == b && checkTriples(w,u,h,d,g,b)){
                            poss.get("w")[w]=true;
                            poss.get("u")[u]=true;
                            poss.get("h")[h]=true;
                            poss.get("d")[d]=true;
                            poss.get("g")[g]=true;
                            poss.get("b")[b]=true;

                            count++;
                            //print ""+ count +": "+w+" "+u+" "+h+" "+d+" "+g+" "+b+"\n";
                        }
                    }
                }
            }
        }
    }
}
checkImpossible("w","u","h","d","g","b");

print "count: "+count+"\n";

