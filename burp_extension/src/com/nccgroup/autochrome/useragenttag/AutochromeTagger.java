
package com.nccgroup.autochrome.useragenttag;

import burp.api.montoya.BurpExtension;
import burp.api.montoya.MontoyaApi;

public class AutochromeTagger implements BurpExtension {
  @Override
  public void initialize(MontoyaApi api) {
    api.extension().setName("Autochrome Color Tagger");

    /* Register the http handler */
    api.http().registerHttpHandler(new AutochromeHttpHandler(api));
  }
}
