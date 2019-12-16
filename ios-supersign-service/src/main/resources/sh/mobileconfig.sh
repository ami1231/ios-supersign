#!/bin/bash

openssl smime -sign -in udid_temp.mobileconfig -out udid.mobileconfig -signer InnovCertificates.pem -certfile root.crt.pem -outform der -nodetach