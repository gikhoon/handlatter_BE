package handlatter.domain.elastic;

import jakarta.persistence.Id;
import lombok.Getter;
import org.springframework.data.elasticsearch.annotations.Document;

@Getter
@Document(indexName = "letter")
public class Letter {
    @Id
    private String id;
    private Long memberId;
    private String content;
    private String url;

    public Letter(Long memberId, String content, String url) {
        this.memberId = memberId;
        this.content = content;
        this.url = url;
    }
}
