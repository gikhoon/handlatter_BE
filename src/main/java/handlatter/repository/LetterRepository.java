package handlatter.repository;

import handlatter.domain.elastic.Letter;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface LetterRepository extends ElasticsearchRepository<Letter, String> {

    List<Letter> findAllByMemberId(Long memberId);

    @Query("{\"bool\": {\"must\": [{\"match\": {\"content\": \"?0\"}}],\"filter\": [{\"term\": {\"memberId\": \"?1\"}}]}}")
    List<Letter> searchByKeywordAndMemberId(String keyword, Long memberId);
}
