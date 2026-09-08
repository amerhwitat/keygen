package org.chimera.cognition;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.time.Instant;
import java.util.ArrayDeque;
import java.util.HexFormat;
import java.util.Map;

/** Java semantic port of Chimera's bounded evidence/knowledge bus. */
public final class KnowledgeBus {
    public record Evidence(String source,String title,String summary,Instant retrievedAt,String contentHash) {
        public static Evidence fromText(String source,String title,String summary){
            try{var d=MessageDigest.getInstance("SHA-256").digest(summary.getBytes(StandardCharsets.UTF_8));return new Evidence(source,title,summary,Instant.now(),HexFormat.of().formatHex(d));}
            catch(Exception e){throw new IllegalStateException(e);}
        }
    }
    private final int maxItems; private final ArrayDeque<Evidence> items=new ArrayDeque<>();
    public KnowledgeBus(){this(10_000);} public KnowledgeBus(int maxItems){if(maxItems<1)throw new IllegalArgumentException();this.maxItems=maxItems;}
    public synchronized void add(Evidence e){items.addLast(e);while(items.size()>maxItems)items.removeFirst();}
    public synchronized java.util.List<Evidence> snapshot(){return java.util.List.copyOf(items);}
}
