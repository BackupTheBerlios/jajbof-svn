README
==================
:Author:    Kalkin Sam
:Email:     mail@kalkin.de
:Date:      2008-09-26

Writing Own Moduls
------------------
Your Modul hasto implement the interface JAJBofModul and have a constructor
which looks like this:
public YourModul(XMPPConnection connection){}

Thats it! At least at the moment, because there is no dynamic classloader.
