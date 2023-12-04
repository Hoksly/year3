require 'date'
require 'minitest/autorun'

require_relative 'task'

class LibraryTest < Minitest::Test

  def setup
    publ1 = Publication.new('The Ruby Programming Language', 'David Flanagan, Yukihiro Matsumoto', ['ruby', 'programming'])
    publ2 = Publication.new('Programming Ruby 1.9 & 2.0: The Pragmatic Programmers Guide', 'Dave Thomas, Andy Hunt, Chad Fowler', ['ruby', 'programming'])
    publ3 = Publication.new('Eloquent Ruby', 'Russ Olsen', ['ruby', 'programming', 'beginner'])
    publ4 = Publication.new('The Well-Grounded Rubyist', 'David A. Black', ['ruby', 'programming', 'beginner'])
    publ5 = Publication.new('Ruby Cookbook', 'Lucas Carlson, Leonard Richardson', ['ruby', 'programming', 'cookbook'])
    publ6 = Publication.new('The Ruby Way', 'Hal Fulton, Andre Arko', ['ruby', 'programming', 'beginner'])

    journal_article1 = JournalArticle.new('Ruby: An Introduction', 'Yukihiro Matsumoto', 'Communications of the ACM', 52, 2, ['ruby', 'programming', 'beginner'])
    journal_article2 = JournalArticle.new('Ruby: A Dynamic and Open Language', 'Yukihiro Matsumoto', 'IEEE Computer', 38, 9, ['ruby', 'programming', 'beginner'])
    journal_article3 = JournalArticle.new('Ruby on Rails', 'David Heinemeier Hansson', 'IEEE Software', 23, 6, ['ruby', 'programming', 'rails'])
    journal_article4 = JournalArticle.new('Ruby on Rails', 'David Heinemeier Hansson', 'IEEE Software', 23, 6, ['ruby', 'programming', 'rails'])

    conference_paper1 = ConferencePaper.new('RubyConf 2013 Keynote', 'Matz', 'RubyConf', Date.new(2013, 11, 8), ['ruby', 'programming'])
    conference_paper2 = ConferencePaper.new('RubyConf 2014 Keynote', 'Matz', 'RubyConf', Date.new(2014, 11, 15), ['ruby', 'programming'])
    conference_paper3 = ConferencePaper.new('RubyConf 2015 Keynote', 'Matz', 'RubyConf', Date.new(2015, 11, 13), ['ruby', 'programming'])
    conference_paper4 = ConferencePaper.new('RubyConf 2016 Keynote', 'Matz', 'RubyConf', Date.new(2016, 11, 11), ['ruby', 'programming'])

    @library = Library.new

    @library.add_publication(publ1)
    @library.add_publication(publ2)
    @library.add_publication(publ3)
    @library.add_publication(publ4)
    @library.add_publication(publ5)
    @library.add_publication(publ6)

    @library.add_publication(journal_article1)
    @library.add_publication(journal_article2)
    @library.add_publication(journal_article3)
    @library.add_publication(journal_article4)

    @library.add_publication(conference_paper1)
    @library.add_publication(conference_paper2)
    @library.add_publication(conference_paper3)
    @library.add_publication(conference_paper4)

  end


  def test_1
    assert_equal(14, @library.search_by_title('Ruby').size)
  end

  def test_2
    assert_equal(4, @library.search_by_author('Matz').size)
  end

  def test_3
    assert_equal(14, @library.search_by_keywords('ruby').size)
  end

  def test_3
    assert_equal(2, @library.search_by_keywords('rails').size)
  end

  def test_4()
    result = @library.search_by_relevance('Ruby')

    
    assert_equal('The Ruby Programming Language', result[0].title)
    assert_equal('Programming Ruby 1.9 & 2.0: The Pragmatic Programmers Guide', result[1].title)
    assert_equal('Eloquent Ruby', result[2].title)
  end
end