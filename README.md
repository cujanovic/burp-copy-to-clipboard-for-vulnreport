# Burp Copy to Clipboard for VulnReport

***

"With Burp Copy to Clipboard for VulnReport you will be able to right-click any request/response in Burp and copy the data to a string which can be decoded by Vulnreport when pasted in this section type. The decoded request/response and URL will be added as vuln data sections."

Based on: https://github.com/salesforce/vulnreport/blob/master/vulnreport.jar

***

### Build:
`git clone https://github.com/cujanovic/burp-copy-to-clipboard-for-vulnreport`

`cd burp-copy-to-clipboard-for-vulnreport/`

`javac -d build src\/burp\/*.java -Xlint:deprecation`

`jar cf bin\/copy-to-clipboard-for-vulnreport-1.0.jar -C build burp`

***

### Documentation
http://vulnreport.io/documentation
