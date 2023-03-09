# Autochrome Burp Extension

This extension provides Autochrome integration for Burp Suite. It automatically populates the "comment" field in the proxy history based on the Autochrome profile in user.

It does so by looking for the string "autochrome/\[XXXX\]" in the User-Agent header. If this string is present, it takes the XXXX and sets it as the request comment in the proxy history.

# Installation

1. `gradle build`
1. The final JAR will be on "build/libs/"
1. In Burp, go to Extender => Extensions.
1. Select "Add". Select the JAR file and finish the wizard.
