from Dialog_Generator import *

typeOfDialog = [BusinessDialog,
                AgriculturalDialog,
                TeacherDialog,
                ITDialog,
                MedicineDialog,
                LawyerDialog,
                ScientistDialog,
                EngineerDialog,
                BasicDialog,
                ]
for dialog in typeOfDialog:
	obj = dialog()
	obj.createDialog()  # minSize - maxSize -> Размер диалога
	print(f'Генерируем {obj.__class__.__name__}!')
	
post = Post()
post.createPost()
print(f'Генерируем {post.__class__.__name__}!')

print('Complete!')
