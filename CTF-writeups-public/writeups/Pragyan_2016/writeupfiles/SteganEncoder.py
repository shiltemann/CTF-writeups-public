
import cImage as image
import os
import math
import argparse
import sys
from random import randint

class SteganEncoder(object):


	def __init__(self, textsource = None, imagesource = None, imagedestination = None, keyoption = None, forceoption = None) :

		if imagesource == None :
			sys.exit("\n!! Error : Image source location not specified \n")
		else :
			if not self.valid(imagesource, 0) :
				sys.exit("\n!! Error : Invalid image source location \n")	

		if textsource == None :
			sys.stdout.write("\n## Enter data to be encoded : ")
			textsource = input("")
		else :
			if self.valid(textsource, 1) :
				textsource = self.fetch_data_from_source(textsource)
			else : 
				sys.exit("\n!! Error : Invalid text data source location \n")					

		if imagedestination == None :
			sys.stdout.write("\n## Alert : Image_Destination not specified, default destination address -> './encoded_stegan.png'\n")
			imagedestination = 'encoded_stegan.png'

		self.textsource = textsource
		self.imagesource = imagesource
		self.imagedestination = imagedestination
		self.forceoption = forceoption
		self.keyoption = keyoption

		steganSymbolArray = []
		steganSymbolArray.append({'runlength':3,'value':1})
		steganSymbolArray.append({'runlength':2,'value':0})
		steganSymbolArray.append({'runlength':5,'value':1})
		steganSymbolArray.append({'runlength':1,'value':0})
		steganSymbolArray.append({'runlength':1,'value':1})
		steganSymbolArray.append({'runlength':2,'value':0})
		steganSymbolArray.append({'runlength':2,'value':1})
		steganSymbolArray.append({'runlength':2,'value':0})
		steganSymbolArray.append({'runlength':1,'value':1})
		steganSymbolArray.append({'runlength':1,'value':0})
		steganSymbolArray.append({'runlength':3,'value':1})
		steganSymbolArray.append({'runlength':2,'value':0})
		steganSymbolArray.append({'runlength':5,'value':1})
		steganSymbolArray.append({'runlength':4,'value':0})
		steganSymbolArray.append({'runlength':3,'value':1})
		steganSymbolArray.append({'runlength':1,'value':0})
		steganSymbolArray.append({'runlength':3,'value':1})
		steganSymbolArray.append({'runlength':1,'value':0})

		self.steganSymbolArray = steganSymbolArray

		
	def valid(self, sourcelocation, type) :

		if (not os.path.exists(sourcelocation)) :
			return False
		
		if type == 1 :
			if os.stat(sourcelocation).st_size == 0 :
				return False
			
		return True


	def fetch_data_from_source(self, textsource) :

		file_read = open(textsource, 'r')
		textData = file_read.read()
		return textData


	def runlengthencoder(self):

		textData = self.textsource
		#print(textData)
		binaryData = ''.join(format(ord(x),'#09b')[2:] for x in textData)
		#print(binaryData)

		dataArray = []

		count = 0
		check_bit = binaryData[0]

		for bit in binaryData:
			if check_bit == bit :
				if count == 15 : 
					node = {}
					node['runlength'] = count
					node['value'] = check_bit
					dataArray.append(node)
					count = 1
				else :
					count = count + 1
			else :
				node = {}
				node['runlength'] = count
				node['value'] = check_bit
				dataArray.append(node)
				count = 1
				check_bit = bit


		node = {}
		node['runlength'] = count
		node['value'] = check_bit

		dataArray.append(node)

		#print("hello")
		#for x in dataArray:
		#	print(x['runlength'])
		#	print(x['value'])
		#	print("\n")
		
		return dataArray


	def steganize(self, dataArray, maxRunlenAdd):

		img = image.Image(self.imagesource)
		imgHeight = img.getHeight()
		imgWidth = img.getWidth() 

		'''
		Adding steganHeader, reserved math.floor(imgHeight/10) number of lines for that purpose
		'''

		idx = 0
		idx_flag = 0
		headerHeight = math.floor(imgHeight/10)

		newIm = image.EmptyImage(imgWidth,imgHeight)

		for row in range(imgHeight) : 
			for col in range(imgWidth) :
				oldPix = img.getPixel(col,row)
				newIm.setPixel(col,row,oldPix)

		markerArray = self.steganSymbolArray

		for row in range(0,1) :
			for col in range(1,19) :
				pixel = newIm.getPixel(col,row)
				pixR = pixel.getRed()
				pixG = pixel.getGreen()
				pixB = pixel.getBlue()

				binaryStrR = format(pixR,'#10b')[2:]
				binaryStrG = format(pixG,'#10b')[2:]
				binaryStrB = format(pixB,'#10b')[2:]

				#sys.stdout.write("\n");
				#sys.stdout.write(binaryStrR);
				#sys.stdout.write(" ");
				#sys.stdout.write(binaryStrG);
				#sys.stdout.write(" ");
				#sys.stdout.write(binaryStrB);
				#sys.stdout.write("\n");

				
				listStrR = list(binaryStrR)
				listStrG = list(binaryStrG)
				listStrB = list(binaryStrB)

				listStrR[6] = format(markerArray[idx]['runlength'],'#06b')[2:][0]
				listStrR[7] = format(markerArray[idx]['runlength'],'#06b')[2:][1]

				listStrG[6] = format(markerArray[idx]['runlength'],'#06b')[2:][2]
				listStrG[7] = format(markerArray[idx]['runlength'],'#06b')[2:][3]

				listStrB[7] = format(int(markerArray[idx]['value']),'#03b')[2:][0]



				binaryStrR = ''.join(c for c in listStrR if c not in 'b')
				binaryStrG = ''.join(c for c in listStrG if c not in 'b')
				binaryStrB = ''.join(c for c in listStrB if c not in 'b')

				#sys.stdout.write("\n");
				#sys.stdout.write(binaryStrR);
				#sys.stdout.write(" ");
				#sys.stdout.write(binaryStrG);
				#sys.stdout.write(" ");
				#sys.stdout.write(binaryStrB);
				#sys.stdout.write("\n");

				pixN = image.Pixel(int(binaryStrR,2),int(binaryStrG,2),int(binaryStrB,2))
				newIm.setPixel(col,row,pixN)

				idx = idx + 1
				if idx < len(dataArray) :
					continue
				else :
					idx_flag = 1
					break
	
			if idx_flag == 1 : 
				break

		idx = 0
		idx_flag = 0
		adder_list = []

		for row in range(headerHeight+1,imgHeight-1) :

			for col in range(1,imgWidth-1) :

				if self.keyoption : 
					adder = randint(1,15 - maxRunlenAdd)
					adder_list.append(str(adder))
				else :
					adder = 0

				pixel = newIm.getPixel(col,row)
				pixR = pixel.getRed()
				pixG = pixel.getGreen()
				pixB = pixel.getBlue()

				binaryStrR = format(pixR,'#10b')[2:]
				binaryStrG = format(pixG,'#10b')[2:]
				binaryStrB = format(pixB,'#10b')[2:]

				#sys.stdout.write("\n Old ");
				#sys.stdout.write(binaryStrR);
				#sys.stdout.write(" ");
				#sys.stdout.write(binaryStrG);
				#sys.stdout.write(" ");
				#sys.stdout.write(binaryStrB);
				#sys.stdout.write("\n");

				
				listStrR = list(binaryStrR)
				listStrG = list(binaryStrG)
				listStrB = list(binaryStrB)

				listStrR[6] = format(dataArray[idx]['runlength'] + adder,'#06b')[2:][0]
				listStrR[7] = format(dataArray[idx]['runlength'] + adder,'#06b')[2:][1]

				listStrG[6] = format(dataArray[idx]['runlength'] + adder,'#06b')[2:][2]
				listStrG[7] = format(dataArray[idx]['runlength'] + adder,'#06b')[2:][3]

				listStrB[7] = format(int(dataArray[idx]['value']),'#03b')[2:][0]

				binaryStrR = ''.join(c for c in listStrR if c not in 'b')
				binaryStrG = ''.join(c for c in listStrG if c not in 'b')
				binaryStrB = ''.join(c for c in listStrB if c not in 'b')

				#sys.stdout.write("\n New ");
				#sys.stdout.write(binaryStrR);
				#sys.stdout.write(" ");
				#sys.stdout.write(binaryStrG);
				#sys.stdout.write(" ");
				#sys.stdout.write(binaryStrB);
				#sys.stdout.write("\n");


				try :
					pixN = image.Pixel(int(binaryStrR,2),int(binaryStrG,2),int(binaryStrB,2))
					newIm.setPixel(col,row,pixN)
				except ValueError :
					sys.exit("!! Error : Unexpected error, please try again using a different image or text \n")					

				idx = idx + 1
				if idx < len(dataArray) :
					continue
				else :
					idx_flag = 1
					break
	
			if idx_flag == 1 : 
				break

		recLen = len(dataArray)

		#print (recLen)
		recLen = format(recLen,'#026b')[2:]

		for row in range(1,2) :
			j=0
			for col in range(1,5) :

				pixel = newIm.getPixel(col,row)
				pixR = pixel.getRed()
				pixG = pixel.getGreen()
				pixB = pixel.getBlue()

				binaryStrR = format(pixR,'#10b')[2:]
				binaryStrG = format(pixG,'#10b')[2:]
				binaryStrB = format(pixB,'#10b')[2:]
				
				listStrR = list(binaryStrR)
				listStrG = list(binaryStrG)
				listStrB = list(binaryStrB)

				listStrR[6] = recLen[j+0]
				listStrR[7] = recLen[j+1]

				listStrG[6] = recLen[j+2]
				listStrG[7] = recLen[j+3]

				listStrB[6] = recLen[j+4]
				listStrB[7] = recLen[j+5]

				binaryStrR = ''.join(c for c in listStrR if c not in 'b')
				binaryStrG = ''.join(c for c in listStrG if c not in 'b')
				binaryStrB = ''.join(c for c in listStrB if c not in 'b')

				pixN = image.Pixel(int(binaryStrR,2),int(binaryStrG,2),int(binaryStrB,2))
				newIm.setPixel(col,row,pixN)

				j = j + 6

		
		newIm.save(self.imagedestination)
		if self.keyoption :
			sys.stdout.write('## Status : Encoding completed , Key : \n%s \n' % ''.join(adder_list))
		else :
			sys.stdout.write('## Status : Encoding completed ')


	def compatibility(self):

		img = image.Image(self.imagesource)
		imgHeight = img.getHeight()
		imgWidth = img.getWidth() 

		'''
		Minimum dimensions
			Height : 32
			Width  : 32
		'''

		if (imgHeight < 32) or (imgWidth < 32) :
			sys.exit("\n!! Error : Source image not compatible [minimum dimension -> 32 x 32] \n")			

		headerHeight = math.floor(imgHeight / 10)
		textHeight = imgHeight - headerHeight
		textWidth = imgWidth

		totalEncodableLength = textWidth * textHeight

		'''
		If image was already steganized, caution-prompt displayed
		'''

		'''
		Fill this variable with runLengthEncoded 'stegan' , key used to tell decoder or encoder that image contains data

		'''

		match_SteganSymbol=0

		for row in range(0,1) :
			for column in range (1,19) :
				pix = img.getPixel(column,row)
				pixR = format(pix.getRed(), '#10b')[2:]
				pixG = format(pix.getGreen(), '#10b')[2:]
				pixB = format(pix.getBlue(), '#10b')[2:]

				#print("R: %s G: %s B: %s" % (bin(pixR)[2:],bin(pixG)[2:],bin(pixB)[2:]))
				runLength_SteganSymbol = (int(pixR[6]) * 8) + (int(pixR[7]) * 4) + (int(pixG[6]) * 2) + (int(pixG[7]) * 1)
				value_SteganSymbol = int(pixB[7])

				if (runLength_SteganSymbol == self.steganSymbolArray[column-1]['runlength']) and (value_SteganSymbol == self.steganSymbolArray[column-1]['value']) : 
					match_SteganSymbol = match_SteganSymbol + 1
				else : 
					break

		#print("Number of matches : %d" % match_SteganSymbol)
		if not self.forceoption :
			if match_SteganSymbol == 18 :
				sys.exit("\n!! Error : Image already contains steganized data [Use '-force' to force new steganization] \n")

		return totalEncodableLength


	def run(self):

		dataArray = self.runlengthencoder()
		totalEncodableLength = self.compatibility()

		if(len(dataArray) >= totalEncodableLength)	:
			sys.exit("\n!! Error : Text too big for image, please choose a bigger image or smaller text to encode \n")

		maxRunLen = 0
		for ele in dataArray :
			if ele['runlength'] > maxRunLen :
				maxRunLen = ele['runlength']

		sys.stdout.write('## Status : Encoding [Could take some time depending on image dimensions]... \n')
		self.steganize(dataArray, maxRunLen)
		

def main() : 
	try:
		parser = argparse.ArgumentParser(description = "Encodes textual data into images")
		parser.add_argument('-force', dest='forceoption', action='store_const', const=True, default=False, help='To force encode data into an already steganized image')
		parser.add_argument('-key', dest='keyoption', action='store_const', const=True, default=False, help='To create a key for decoding purpose')
		parser.add_argument('Image_Source', help = 'Source image file location')
		parser.add_argument('Text_Source', nargs = '?', default = None, help = 'Text data file location')
		parser.add_argument('Image_Destination',nargs = '?', default = None, help = 'Steganized image file destination')
		
		args = parser.parse_args()

		stegan_obj = SteganEncoder(args.Text_Source, args.Image_Source, args.Image_Destination, args.keyoption, args.forceoption)
		stegan_obj.run()

	except KeyboardInterrupt:
		sys.exit("\nProgram was closed by user\n")
		

if __name__=='__main__':
	main()





