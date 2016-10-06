# HawkCD
## Continuous Delivery Software

[![Join the chat at https://gitter.im/rndsolutions/hawkcd](https://badges.gitter.im/rndsolutions/hawkcd.svg)](https://gitter.im/rndsolutions/hawkcd?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge&utm_content=badge)
[![Build Status](https://travis-ci.org/rndsolutions/hawkcd.svg?branch=master)](https://travis-ci.org/rndsolutions/hawkcd)
[![Coverage Status](https://coveralls.io/repos/github/rndsolutions/hawk/badge.svg?branch=master)](https://coveralls.io/github/rndsolutions/hawk?branch=master)

The HawkCD intends to be one stop solution for managing Continuous Delivery processes for the Enterprise.  It  enables and encourages collaboration between delivery team members including but not limited to  Developers, QA, Ops and Release Managers.   

###Setting up build enviroment 

```bash

#git
sudo apt-get install git

#java
sudo add-apt-repository ppa:webupd8team/java
sudo apt-get update
sudo apt-get install oracle-java8-installer

#gradle
sudo add-apt-repository ppa:cwchien/gradle
sudo apt-get update
sudo apt-get install gradle

#nodejs
sudo apt-get install build-essential
curl -sL https://deb.nodesource.com/setup_6.x | sudo -E bash -
sudo apt-get install -y nodejs

#gulp & bower
sudo npm install gulp -g
sudo npm install bower -g
```

###License

Hawkengine is an open source project, sponsored by <a href="http://rnd-solutions.net/">R&D Solutions Ltd.</a> under the <a href="http://www.apache.org/licenses/LICENSE-2.0">Apache License, Version 2.0</a>.

