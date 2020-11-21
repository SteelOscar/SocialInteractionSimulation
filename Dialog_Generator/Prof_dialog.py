from .Basic_Dialog import *
import random


class AgriculturalDialog(BasicDialog):
	def __init__(self):
		super().__init__()
		self.source = 'AgriculturalSource.txt'
		self.out = 'AgriculturalDialog.txt'
		self.pathIn = os.path.join(DICTIONARIES, self.source)
		self.pathOut = os.path.join(DIALOGS, self.out)
		self.chance = 25  # Шанс прока частицы-вствки


class BusinessDialog(BasicDialog):
	def __init__(self):
		super().__init__()
		self.source = 'BusinessSource.txt'
		self.out = 'BusinessDialog.txt'
		self.pathIn = os.path.join(DICTIONARIES, self.source)
		self.pathOut = os.path.join(DIALOGS, self.out)
		self.chance = 25  # Шанс прока частицы-вствки


class EngineerDialog(BasicDialog):
	def __init__(self):
		super().__init__()
		self.source = 'EngineerSource.txt'
		self.out = 'EngineerDialog.txt'
		self.pathIn = os.path.join(DICTIONARIES, self.source)
		self.pathOut = os.path.join(DIALOGS, self.out)
		self.chance = 25  # Шанс прока частицы-вствки


class ITDialog(BasicDialog):
	def __init__(self):
		super().__init__()
		self.source = 'ITSource.txt'
		self.out = 'ITDialog.txt'
		self.pathIn = os.path.join(DICTIONARIES, self.source)
		self.pathOut = os.path.join(DIALOGS, self.out)
		self.chance = 25  # Шанс прока частицы-вствки


class LawyerDialog(BasicDialog):
	def __init__(self):
		super().__init__()
		self.source = 'LawyerSource.txt'
		self.out = 'LawyerDialog.txt'
		self.pathIn = os.path.join(DICTIONARIES, self.source)
		self.pathOut = os.path.join(DIALOGS, self.out)
		self.chance = 25  # Шанс прока частицы-вствки


class MedicineDialog(BasicDialog):
	def __init__(self):
		super().__init__()
		self.source = 'MedicineSource.txt'
		self.out = f'MedicineDialog.txt'
		self.pathIn = os.path.join(DICTIONARIES, self.source)
		self.pathOut = os.path.join(DIALOGS, self.out)
		self.chance = 25  # Шанс прока частицы-вствки


class ScientistDialog(BasicDialog):
	def __init__(self):
		super().__init__()
		version = random.randint(1, 2)
		self.source = f'ScientistSource_v{version}.txt'
		self.out = f'ScientistDialog.txt'
		self.pathIn = os.path.join(DICTIONARIES, self.source)
		self.pathOut = os.path.join(DIALOGS, self.out)
		self.chance = 25  # Шанс прока частицы-вствки


class TeacherDialog(BasicDialog):
	def __init__(self):
		super().__init__()
		self.source = 'TeacherSource.txt'
		self.out = 'TeacherDialog.txt'
		self.pathIn = os.path.join(DICTIONARIES, self.source)
		self.pathOut = os.path.join(DIALOGS, self.out)
		self.chance = 25  # Шанс прока частицы-вствки


class Post(BasicDialog):
	def __init__(self):
		super().__init__()
		self.source = 'PostSource.txt'
		self.out = 'Post.txt'
		self.pathIn = os.path.join(DICTIONARIES, self.source)
		self.pathOut = os.path.join(DIALOGS, self.out)
		self.chance = 0  # Шанс прока частицы-вствки
