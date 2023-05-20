---
layout: writeup
title: 'Dec 19: Soap Riddler'
level:
difficulty:
points:
categories: []
tags: []
flag: HV15-uUIh-wudK-YAam-fIw5-YuNo

---

## Challenge

*be fast or be last*

You will be given the solution, your task is to calculate the beautiful
assignment.
But you have to be quick, or Thumper robs your nugget!

[your daily soap](writeupfiles/dec19_wsdl.xml)

## Solution

We have to get our nugget from a SOAP service described by the following
WSDL:

    <?xml version="1.0" encoding="utf-8"?>
    <definitions
    	xmlns:SOAP-ENV="http://schemas.xmlsoap.org/soap/envelope/"
    	xmlns:xsd="http://www.w3.org/2001/XMLSchema"
    	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    	xmlns:SOAP-ENC="http://schemas.xmlsoap.org/soap/encoding/"
    	xmlns:tns="urn:hlserver"
    	xmlns:soap="http://schemas.xmlsoap.org/wsdl/soap/"
    	xmlns:wsdl="http://schemas.xmlsoap.org/wsdl/"
    	xmlns="http://schemas.xmlsoap.org/wsdl/"
    	targetNamespace="urn:hlserver">

    <message name="sessionRequest">
    </message>
    <message name="sessionResponse">
      <part name="session" type="xsd:string" />
    </message>

    <message name="questRequest">
      <part name="session" 	type="xsd:string" />
    </message>
    <message name="questResponse">
      <part name="quest" 	type="xsd:string" />
    </message>

    <message name="solutionRequest">
      <part name="session" 	type="xsd:string" />
      <part name="solution" type="xsd:string" />
    </message>
    <message name="solutionResponse">
      <part name="result" 	type="xsd:string" />
    </message>

    <portType name="hlserverPortType">
      <operation name="getSession">
        <documentation>Order new Session</documentation>
        <input 	message="tns:sessionRequest"/>
        <output message="tns:sessionResponse"/>
      </operation>
      <operation name="getQuest">
        <documentation>Order new Quest to solve</documentation>
        <input 	message="tns:questRequest"/>
        <output message="tns:questResponse"/>
      </operation>
      <operation name="submitSolution">
        <documentation>Submit solution for a quest</documentation>
        <input 	message="tns:solutionRequest"/>
        <output message="tns:solutionResponse"/>
      </operation>
    </portType>

    <binding name="hlserverBinding" type="tns:hlserverPortType">
      <soap:binding style="rpc" transport="http://schemas.xmlsoap.org/soap/http"/>
      <operation name="getSession">
        <soap:operation soapAction="urn:hlserver#getSession" style="rpc"/>
        <input>
          <soap:body use="encoded" namespace="urn:hlserver" encodingStyle="http://schemas.xmlsoap.org/soap/encoding/"/>
        </input>
        <output>
          <soap:body use="encoded" namespace="urn:hlserver" encodingStyle="http://schemas.xmlsoap.org/soap/encoding/"/>
        </output>
      </operation>
      <operation name="getQuest">
        <soap:operation soapAction="urn:hlserver#getQuest" style="rpc"/>
        <input>
          <soap:body use="encoded" namespace="urn:hlserver" encodingStyle="http://schemas.xmlsoap.org/soap/encoding/"/>
        </input>
        <output>
          <soap:body use="encoded" namespace="urn:hlserver" encodingStyle="http://schemas.xmlsoap.org/soap/encoding/"/>
        </output>
      </operation>
      <operation name="submitSolution">
        <soap:operation soapAction="urn:hlserver#submitSolution" style="rpc"/>
        <input>
          <soap:body use="encoded" namespace="urn:hlserver" encodingStyle="http://schemas.xmlsoap.org/soap/encoding/"/>
        </input>
        <output>
          <soap:body use="encoded" namespace="urn:hlserver" encodingStyle="http://schemas.xmlsoap.org/soap/encoding/"/>
        </output>
      </operation>
    </binding>
    <service name="hlserver">
      <port name="hlserverPort" binding="tns:hlserverBinding">
        <soap:address location="http://hackvent.org/DailyS04p/server.php"/>
      </port>
    </service>
    </definitions>
{: .language-xml}

So it appears that we need to get a `quest` from the service and submit
a solution. We request a `quest` and get the following:

    x*y*z = 34557834247

It would appear we need to find three factors of a large number.
Submitting a (wrong) solution triggers the following response from the
server:

    nope, this is not the assignment for the given result! (order numbers, no blanks)

So we need to order our numbers (ascending as it turns out) when we
craft our solution, which for this example would look like:

    1447*3323*7187

when we submit this correct solution we get the response:

    Well done, congrats: 1 of 20

So let's automate this and submit 20 solutions in a row

    from suds.client import Client
    import math,itertools

    # return list of all factors of n
    def factors(n):
        fact=[1,n]
        check=2
        rootn=math.sqrt(n)
        while check<rootn:
            if n%check==0:
                fact.append(check)
                fact.append(n/check)
            check+=1
        if rootn==check:
            fact.append(check)
            fact.sort()
        return fact


    # connect to SOAP service and print some info and start a session
    url = 'http://hackvent.org/DailyS04p/server.php?wsdl'
    client = Client(url)
    print client
    session = client.service.getSession()

    # solve 20 quest in a row
    for _ in range(0,20):
        quest = client.service.getQuest(session)

        print 'Quest: '+quest
        equation=quest.split(' = ')
        target= int(equation[1])

        ft= factors(target)

        for c in itertools.combinations(ft,3):
            if c[0]*c[1]*c[2]==target and 1 not in c:
                solstring = str('*'.join(map(str,sorted(c))))
                print 'Our solution: '+solstring
                response = client.service.submitSolution(session,solstring )
                print 'Response: '+response
{: .language-python}

This script outputs the following:

    Service ( hlserver ) tns="urn:hlserver"
       Prefixes (0)
       Ports (1):
          (hlserverPort)
             Methods (3):
                getQuest(xs:string session, )
                getSession()
                submitSolution(xs:string session, xs:string solution, )
             Types (0):


    Quest: x*y*z = 18081466231
    Our solution: 1321*2333*5867
    Response: Well done, congrats: 1 of 20
    Quest: x*y*z = 322993008917
    Our solution: 5099*7307*8669
    Response: Well done, congrats: 2 of 20
    Quest: x*y*z = 86869597361
    Our solution: 2539*4603*7433
    Response: Well done, congrats: 3 of 20
    Quest: x*y*z = 746546585201
    Our solution: 8059*9311*9949
    Response: Well done, congrats: 4 of 20
    Quest: x*y*z = 139364239493
    Our solution: 3989*5573*6269
    Response: Well done, congrats: 5 of 20
    Quest: x*y*z = 609858270569
    Our solution: 7589*8863*9067
    Response: Well done, congrats: 6 of 20
    Quest: x*y*z = 48438366677
    Our solution: 1459*5431*6113
    Response: Well done, congrats: 7 of 20
    Quest: x*y*z = 165453576851
    Our solution: 5233*5281*5987
    Response: Well done, congrats: 8 of 20
    Quest: x*y*z = 45624244177
    Our solution: 1987*2689*8539
    Response: Well done, congrats: 9 of 20
    Quest: x*y*z = 158666169521
    Our solution: 3673*6287*6871
    Response: Well done, congrats: 10 of 20
    Quest: x*y*z = 5458915087
    Our solution: 1031*1087*4871
    Response: Well done, congrats: 11 of 20
    Quest: x*y*z = 299945937959
    Our solution: 3947*7741*9817
    Response: Well done, congrats: 12 of 20
    Quest: x*y*z = 73131129427
    Our solution: 2719*4027*6679
    Response: Well done, congrats: 13 of 20
    Quest: x*y*z = 179127963941
    Our solution: 2267*8273*9551
    Response: Well done, congrats: 14 of 20
    Quest: x*y*z = 80209455857
    Our solution: 1499*7001*7643
    Response: Well done, congrats: 15 of 20
    Quest: x*y*z = 79052863993
    Our solution: 1451*5783*9421
    Response: Well done, congrats: 16 of 20
    Quest: x*y*z = 67753202401
    Our solution: 1609*4241*9929
    Response: Well done, congrats: 17 of 20
    Quest: x*y*z = 146094089213
    Our solution: 3851*4243*8941
    Response: Well done, congrats: 18 of 20
    Quest: x*y*z = 32951598587
    Our solution: 2099*2957*5309
    Response: Well done, congrats: 19 of 20
    Quest: x*y*z = 63435256273
    Our solution: 3761*3889*4337
    Response: Congrats, your HV-Nugget is HV15-uUIh-wudK-YAam-fIw5-YuNo

After 20 successful quests we are given the nugget.



