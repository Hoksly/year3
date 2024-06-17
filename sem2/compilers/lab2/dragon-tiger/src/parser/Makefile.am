## src/parser/Makefile.am

# Bison options
AM_YFLAGS = -d -v --report-file=bison-report.txt --graph=bison-graph.gv -Wno-yacc

# Flex options
AM_LFLAGS = -otiger_lexer.cc

# Libraries to be built
noinst_LIBRARIES = libparser.a
libparser_a_SOURCES = tiger_parser.yy tiger_lexer.ll parser_driver.cc parser_driver.hh
AM_CXXFLAGS = -pedantic -Wall

# Extra distribution files
EXTRA_DIST = tiger_parser.hh tiger_parser.cc tiger_lexer.cc location.hh stack.hh position.hh

# Clean files
CLEANFILES = tiger_parser.hh tiger_parser.cc tiger_lexer.cc location.hh stack.hh position.hh
