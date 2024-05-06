/*package controllers.Front;
import java.io.IOException;

import fi.iki.elonen.NanoHTTPD;

public class OAuthCallbackServer extends NanoHTTPD {

        private static final int PORT = 8080;

        public OAuthCallbackServer() throws IOException {
            super(PORT);
            start(NanoHTTPD.SOCKET_READ_TIMEOUT, false);
            System.out.println("Server started on port " + PORT);
        }

        @Override
        public Response serve(IHTTPSession session) {
            String uri = session.getUri();
            if (uri.equals("/oauth/callback")) {
                // Handle OAuth callback
                String authorizationCode = session.getParms().get("code");
                // Notify JavaFX application about authorization code
                System.out.println("Authorization code received: " + authorizationCode);
                return newFixedLengthResponse("Authorization code received: " + authorizationCode);
            } else {
                return super.serve(session);
            }
        }

        public static void main(String[] args) {
            try {
                new OAuthCallbackServer();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

}
*/