#!/bin/bash

openssl smime -sign -in $1 -out $2 -signer $3 -certfile $4 -outform der -nodetach