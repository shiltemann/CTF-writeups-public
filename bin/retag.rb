require 'pp'

# 30-virtual-series-100-virtual-box-7.md
# 31-web-exploitation-100-meteor-smash.md
# 32-programming-100-qset1.md
# 33-forensics-120-zippy.md

format = ARGV #["idx", "category", "points", "title"]
if ARGV.length == 0
  puts "Provide some of [idx, category, points, title]"
end

def format2match(f)
  f2 = f.map{|f1|
    if f1 == "idx"
      '([0-9]+)'
    elsif f1 == "category"
      '(?<category>[a-z0-9-]*)'
    elsif f1 == "points"
      '(?<points>[0-9]+)'
    elsif f1 == "title"
      '([a-z0-9-]*)'
    end
  }.join("-")
  /#{f2}.md/
end
m = format2match(format)

Dir.glob("*.md").select{|path| path =~ /-.*-/}.each{|path|
  ma = path.match(m)
  lines = File.open(path).read.strip

  if ma["points"]
    points = ma["points"].to_i
    lines.gsub!(/^points:.*$/, "points: #{points}")
  end

  if ma["category"]
    category = ma["category"].gsub(/-/, ' ')
    lines.gsub!(/^categories: \[\]$/, "categories: [#{category}]")
  end

  File.open(path, 'w').write(lines)
}
