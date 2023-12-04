class Publication
  attr_accessor :title, :author, :keywords, :references

  def initialize(title, author, keywords = [])
    @title = title
    @author = author
    @keywords = keywords
    @references = []
  end

  def add_reference(publication)
    @references << publication
  end

  def relevance_to_query(query)
    relevance = 0

    query_words = query.split

    query_words.each do |query_word|
      relevance += 1 if @title.include?(query_word) 
      relevance += 1 if @author.include?(query_word) 
       relevance += 1 if @keywords.include?(query_word)
    end

    relevance
  
  end
  
end

class JournalArticle < Publication
  attr_accessor :journal_name, :volume, :issue

  def initialize(title, author, journal_name, volume, issue, keywords = [])
    super(title, author, keywords)
    @journal_name = journal_name
    @volume = volume
    @issue = issue
  end

  def relevance_to_query(query)
    relevance = super(query)

    if @volume == query.to_i
      relevance += 1
    end

    if @issue == query.to_i
      relevance += 1
    end

    relevance
  end
end

class ConferencePaper < Publication
  attr_accessor :conference_name, :conference_date

  def initialize(title, author, conference_name, conference_date, keywords = [])
    super(title, author, keywords)
    @conference_name = conference_name
    @conference_date = conference_date
  end

  def relevance_to_query(query)
    relevance = super(query)

    if @conference_date.year == query.to_i
      relevance += 5
    end

    if @conference_name.include?(query)
      relevance += 10
    end

    relevance
  end
end


class Library
  def initialize(publications = [])
    @publications = publications
  end

  def search_by_bibliography(query)
    results = []
  
    @publications.each do |publication|
   
      if publication.title.include?(query) || publication.author.include?(query)
        results << publication
      end
    end
  
    results
  end

  def add_publication(publication)
    @publications << publication
  end


  def search_by_keywords(query)
    results = []
  
    @publications.each do |publication|
      publication.keywords.each do |keyword|
        if keyword.include?(query)
          results << publication
        end
      end
    end
  
    results
  end

  def search_by_author(query)
    results = []

    @publications.each do |publication|
      if publication.author.include?(query)
        results << publication
      end
    end

    return results
  end


  def relevance_to_query(query)
    relevance = 0

    query_words = query.split

    query_words.each do |query_word|
      relevance += 1 if title.include?(query_word) 
      relevance += 1 if author.include?(query_word) 
      relevance += 1 if date.include?(query_word)
      relevance += 1 if keywords.include?(query_word)
    end

    relevance
  
  end



def search_by_relevance(query)
  results = @publications.dup

  results.sort_by do |publication|
    -publication.relevance_to_query(query)
  end

  results
end

  def search_by_title(query)
    results = []
  
    @publications.each do |publication|
      if publication.title.include?(query)
        results << publication.title
      end
    end
  
    results
  end

end
