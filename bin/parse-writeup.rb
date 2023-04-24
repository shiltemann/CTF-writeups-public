require 'kramdown'
require 'kramdown-parser-gfm'

def find_code(e)
  if e.type.to_s == "codeblock"
    return e
  else
    return e.children.map{|w| find_code(w)}.select{|w| ! w.nil? }
  end
end

def parse(doc)
  x = doc.to_html
  id = x.split('>')[0].split('"')[1]
  title = x.split(/[<>]/)[2]
  body = x.split('</h2>')[1]
  if ! body.nil?
    doc.root.children = doc.root.children[1..]
    body = doc.to_kramdown.gsub(/^\*\*(.*)\*\*$/, '## \1')
  end

  flag = nil
  potential_flag = doc.root.children.map{|n| find_code n }.flatten.uniq[-1]
  if ! potential_flag.nil?
    if potential_flag.value.count('{') == 1
    flag = potential_flag.value.strip
    end
  end

  {
    :id => id,
    :title => title,
    :header => {
      "layout" => "writeup",
      "title" => title,
      "level" => nil,
      "difficulty" => nil,
      "points" => nil,
      "categories" => [],
      "tags" => [],
      "flag" => flag
    },
    :body => body,
  }
end


def process(doc)
  res = []
  cached = []
  doc.root.children.each{|e|
    p e
    if e.type.to_s == "header"
      res.append cached
      cached = []
    end
    cached.append e
  }
  res.append cached
  res
end

text = File.open(ARGV[0]).read.strip
doc = Kramdown::Document.new(text, input: 'GFM')

process(doc).select{|x| x.length > 0}.each{|x|
  p "===== #{x.length}"
  doc2 = doc.clone
  doc2.root.children = x
  res = parse(doc2)
  fn = "#{res[:id]}.md"
  handle = File.open(fn, 'w')
  handle.write(res[:header].to_yaml)
  handle.write("---\n")
  handle.write(res[:body])
}
