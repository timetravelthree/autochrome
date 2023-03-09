package com.nccgroup.autochrome.useragenttag;
import static burp.api.montoya.http.handler.RequestToBeSentAction.continueWith;
import static burp.api.montoya.http.handler.ResponseReceivedAction.continueWith;

import burp.api.montoya.MontoyaApi;
import burp.api.montoya.core.Annotations;
import burp.api.montoya.core.HighlightColor;
import burp.api.montoya.http.handler.*;
import burp.api.montoya.http.message.HttpHeader;
import burp.api.montoya.http.message.requests.HttpRequest;
import burp.api.montoya.http.message.responses.HttpResponse;
import burp.api.montoya.logging.Logging;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class AutochromeHttpHandler implements HttpHandler {
  private final Pattern useragentAutochromePattern =
      Pattern.compile("autochrome/(\\w+)");
  private final Logging logging;

  public AutochromeHttpHandler(MontoyaApi api) { this.logging = api.logging(); }

  @Override
  public RequestToBeSentAction
  handleHttpRequestToBeSent(HttpRequestToBeSent requestToBeSent) {
    Annotations annotations = requestToBeSent.annotations();

    /* Check every header in the request to see if the User-Agent header exists
     * and, if it does find the autochrome profile name in it */
    for (HttpHeader header : requestToBeSent.headers()) {
      if (header.name().equalsIgnoreCase("User-Agent")) {
        Matcher m = useragentAutochromePattern.matcher(header.value());

        /* Check if regex matches and if it does procede with annotations */

        if (m.find()) {

          /* If the group exists take it from the Matcher object and make a
           * coloured comment */
          String autochromeProfile = m.group(1);
          String comment =
              "autochrome ".concat(autochromeProfile).concat(" profile");

          switch (autochromeProfile) {
          case "blue":
            annotations = annotations.withHighlightColor(HighlightColor.BLUE);
            break;
          case "cyan":
            annotations = annotations.withHighlightColor(HighlightColor.CYAN);
            break;
          case "green":
            annotations = annotations.withHighlightColor(HighlightColor.GREEN);
            break;
          case "orange":
            annotations = annotations.withHighlightColor(HighlightColor.ORANGE);
            break;
          case "purple":
            annotations =
                annotations.withHighlightColor(HighlightColor.MAGENTA);
            break;
          case "red":
            annotations = annotations.withHighlightColor(HighlightColor.RED);
            break;
          case "white":
            annotations = annotations.withHighlightColor(HighlightColor.GRAY);
            break;
          case "yellow":
            annotations = annotations.withHighlightColor(HighlightColor.YELLOW);
            break;
          default:
            logging.logToError(
                "Profile ".concat(autochromeProfile).concat(" not recognized"));
            break;
          }

          annotations = annotations.withNotes(comment);
        }
      }
    }

    /* Pass the request as it is */
    HttpRequest modifiedRequest = (HttpRequest)requestToBeSent;

    return continueWith(modifiedRequest, annotations);
  }

  /* Has to be implemented for the HttpHandler interface */
  @Override
  public ResponseReceivedAction
  handleHttpResponseReceived(HttpResponseReceived responseReceived) {
    /* Pass the response as it is */
    return continueWith((HttpResponse)responseReceived, null);
  }
}
