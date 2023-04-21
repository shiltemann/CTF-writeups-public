for i in `seq 1 200`; do
	echo $i;
	q=$(python -c "print('+'.join(['True'] * $i))")
	sed -i "s/($q)/($i)/g" True.1337
	q=$(python -c "print('+'.join(['1337'] * $i))")
	sed -i "s/($q)/($i)/g" True.1337
done

sed -i "s/exec/print/g" True.1337
sed -i "s/__1337/print/g" True.1337
sed -i "s/_1337/chr/g" True.1337

python True.1337 > True.1338
