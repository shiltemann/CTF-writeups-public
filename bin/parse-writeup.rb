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

  flag = nil
  potential_flag = doc.root.children.map{|n| find_code n }.flatten.uniq[-1]
  potential_flag_idx = doc.root.children.index{|e| e.type.to_s == "p" && e.children[0].type.to_s == "strong" && e.children[0].children[0].type.to_s == "text"  && e.children[0].children[0].value == "Flag"}

  if ! potential_flag.nil?
    if potential_flag.value.count('{') == 1
    flag = potential_flag.value.strip
    end
  end

  if ! potential_flag_idx.nil?
    doc.root.children = doc.root.children[0..(potential_flag_idx - 1)]
  end

  if ! body.nil?
    # Strip the header
    doc.root.children = doc.root.children[1..]
    body = doc.to_kramdown.gsub(/^\*\*(.*)\*\*$/, '## \1')
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
    if e.type.to_s == "header"
      res.append cached
      cached = []
    end
    cached.append e
  }
  res.append cached
  res
end

text = File.open('writeup.md').read.strip
doc = Kramdown::Document.new(text, input: 'GFM')

parts = process(doc).select{|x| x.length > 0}.map{|x|
  doc2 = doc.clone
  doc2.root.children = x
  parse(doc2)
}

overview_idx = parts.index{|res| res[:title] == "Overview" }
if overview_idx < 3
  parts = parts[(overview_idx + 1)..]
end

parts.each.with_index{|res, idx|
  p "===== #{res[:title]}"
  idx0 = "%02d" % idx
  fn = "#{idx0}-#{res[:id]}.md"
  handle = File.open(fn, 'w')
  handle.write(res[:header].to_yaml)
  handle.write("---\n")
  handle.write(res[:body])
}
