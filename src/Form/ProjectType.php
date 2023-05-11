<?php

namespace App\Form;

use App\Entity\Accident;
use App\Entity\Project;
use Symfony\Bridge\Doctrine\Form\Type\EntityType;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Validator\Constraints\NotBlank;
use Symfony\Component\Validator\Constraints\Length;
use Symfony\Component\Form\Extension\Core\Type\PasswordType;
use Symfony\Component\Form\Extension\Core\Type\FileType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Validator\Constraints as Assert;
use Symfony\Component\Validator\Constraints\Type;

class ProjectType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
            ->add('nom',TextType::class,[
                'constraints' => [
                    new NotBlank([
                        'message' => 'Please enter a name',
                    ]),
                ]])

            ->add('prenom')
            ->add('cin')

            ->add('adresse_mail')
            ->add('num_tel')
            ->add('salaire')
            ->add('disponiblite')
            ->add('lieu_garage')


            ->add('facture_rep',FileType::class,[

                'mapped' => true,
                'required' => false,
                'multiple' => true,



            ])
            ->add('username')
            ->add('password')
        ;
            //->add('created_at')
            //->add('updated_at')
        ;
    }
/*, PasswordType::class, [
    // instead of being set onto the object directly,
    // this is read and encoded in the controller
'mapped' => false,
'required' => false
'attr' => [
'autocomplete' => 'new-password',
'class' => 'form-control'
],
'constraints' => [
new NotBlank([
'message' => 'Please enter a password',
]),
new Length([
'min' => 6,
'minMessage' => 'Your password should be at least {{ limit }} characters',
    // max length allowed by Symfony for security reasons
'max' => 4096,
]),
],
'label' => 'Mot de passe'
])*/
    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            'data_class' => Project::class,
        ]);
    }
}
