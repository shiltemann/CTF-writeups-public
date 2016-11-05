#!/bin/bash
set -ex

convert -rotate -90 warp_speed.5978d1405660e365872cf72dddc7515603f657f12526bd61e56feacf332cccad.jpg 1.jpg

# identify -format "%[EXIF:WinXP-Comments]" warp_speed.5978d1405660e365872cf72dddc7515603f657f12526bd61e56feacf332cccad.jpg | python  -c 'import sys;q=sys.stdin.read().split(", ");w=[x for x in q if x != "0"];print "".join(map(chr, map(int,w)))'
# prints "I am a square. Anyone who tells you otherwise is a LIAR!", not realyl useful

# Split into 8x1000 pixel segments
convert -crop 8x1000  +repage +adjoin 1.jpg 2.jpg

# For each of these segments
for i in {0..31};
do
	# extend the canvas to be 16px by 1000 + 8i, as they're warped up by a uniform amount.
	# Once the canvas has been extended, use gravity to mvoe pixels down and right
	convert 2-$i.jpg -gravity southeast -extent 16x$(echo "1000 + 8 * $i" | bc) -background none 3-$i.jpg;
done;

# Stitch them back together
convert 3-{0..31}.jpg +append 4.jpg;
#convert 3-0.jpg 3-1.jpg 3-2.jpg +append output.jpg

convert -crop 512x500  +repage +adjoin 4.jpg 5.png

for i in {0..2}; do
	convert 5-$i.png -fuzz 3% -transparent white 5-${i}a.png
done;

# Now we use some knowledge from looking at the picture:
# 5-0a is the first segment, we stitch 5-2a on top of it.
convert -composite 5-0a.png 5-2a.png tmp.png


# But we need to shift (with extents again) 5-1a in order to move it 8 pixels right
#convert 5-1a.png -gravity southeast -extent 520x500 5-1b.png
#convert -fuzz 3% -transparent whkkite 5-1b.png 5-1c.png
convert tmp.png -gravity southeast -extent 520x504 tmp2.png
convert -fuzz 3% -transparent white tmp2.png tmp3.png

convert -composite tmp3.png  5-1a.png -flatten out.png




# Cleanup
rm 1.jpg \
2-*.jpg \
3-*.jpg \
4.jpg 5-*.png tmp*.png

