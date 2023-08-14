package shopping.infra;

import java.net.URI;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.web.util.UriComponentsBuilder;

@ConfigurationProperties(prefix = "api")
@ConstructorBinding
public class ExchangeRateApiProperties {

    private final String url;
    private final String quotes;
    private final String source;
    private final String target;
    private final String accessKey;

    public ExchangeRateApiProperties(
            final String url,
            final String quotes,
            final String source,
            final String target,
            final String accessKey
    ) {
        this.url = url;
        this.quotes = quotes;
        this.source = source;
        this.target = target;
        this.accessKey = accessKey;
    }

    public URI getExchangeRateApiURI() {
        return UriComponentsBuilder.fromHttpUrl(this.url)
                .queryParam("access_key", this.accessKey)
                .queryParam("currencies", this.target)
                .queryParam("source", this.source)
                .build().toUri();
    }

    public String getQuotes() {
        return this.quotes;
    }

    public String getSourceToTarget() {
        return this.source + this.target;
    }
}
