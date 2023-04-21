import sys


class QSet:
    def __init__(self, input=None):
        self.qset = {}

        if input:
            for item in input:
                self.add_item(item)

    def add_item(self, item, amt=1):
        if item in self.qset:
            self.qset[item]+=amt
            if self.qset[item]==0:
                del self.qset[item]
        else:
            self.qset[item]=amt

    def add_qset(self, aqset):
        for item in aqset.qset:
            self.add_item(item, aqset.qset[item])

    def remove_qset(self, aqset):
        for item in aqset.qset:
            self.add_item(item, -aqset.qset[item])

    def contains_qset(self, aqset):
        for item in aqset.qset:
            if item in self.qset:
                if self.qset[item]<aqset.qset[item]:
                    return False
            else:
                return False
        return True

    def is_empty(self):
        return len(self.qset)==0

    def __str__(self):
        return str(self.qset)


def parse(z):
    steps = z.strip()
    steps = steps.split(',')
    reps = []
    for step in steps:
        # two / separated arguments
        rep = [y.strip() for y in step.split('/')]
        assert len(rep) == 2

        reps.append(rep)
    return [
        [
            QSet([] if x.split(' ')==[''] else x.split(' '))
            for x in vals
        ]
        for vals in reps
    ]


def interpret_step(program, qset):
    print 'Internal State ', qset.qset, sum([y for (x, y) in qset.qset.items()])
    for process in program:
        print '  Running step ', process[0].qset, process[1].qset
        if qset.contains_qset(process[1]):
            qset.remove_qset(process[1])
            qset.add_qset(process[0])
            return False
    return True


def interpret(program, inputs, maxsteps=100000):
    program = parse(program)
    qset = QSet()

    for idx, val in enumerate(inputs):
        if type(val) in (int, long):
            qset.add_item('i%i' % idx, val)
        else:
            raise Exception("Invalid input type")

    step = 0
    while True:
        if step > maxsteps:
            raise Exception("Too many execution steps!")
        ret = interpret_step(program, qset)
        if ret:
            break
        step += 1

    success = True
    outputs = {}
    for key in qset.qset:
        if key[0] == 'i':
            success = False
            break

        if len(key)>1:
            if key[0]=="o":
                idx = int(key[1:])
                outputs[idx] = qset.qset[key]
            else:
                success = False
                break
        else:
            success = False
            break

    if success:
        outputs_arr = []
        # Implies outputs must be numbered sequentially starting at 0
        for x in range(len(outputs)):
            if x in outputs:
                outputs_arr.append(outputs[x])
            else:
                print outputs
                raise Exception("Invalid output!")
        return outputs_arr
    else:
        print outputs
        raise Exception("Invalid output!")


if __name__ == '__main__':
    program = ','.join([
        'o0/i0',
        'o0/i1',
        'i2/i1',
        'i2/i2',
    ])
    if len(sys.argv) > 1:
        program = sys.argv[1]

    print interpret(program, [4, 2])
