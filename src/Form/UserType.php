<?php

namespace App\Form;

use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Form\Extension\Core\Type\PasswordType;
use Symfony\Component\Form\Extension\Core\Type\RepeatedType;
use Symfony\Component\Validator\Constraints\Email;
use Symfony\Component\Validator\Constraints\NotBlank;
use Symfony\Component\Validator\Constraints as Assert;
use Symfony\Component\Form\Extension\Core\Type\EmailType;
use Symfony\Component\Validator\Constraints\Length;
class UserType extends AbstractType
{
    public function buildForm(FormBuilderInterface $builder, array $options): void
    {
        $builder
        ->add('email', EmailType::class, [
            'constraints' => [
                new NotBlank(['message' => 'Veuillez saisir votre adresse email.']),
                new Email(['message' => 'Veuillez saisir une adresse email valide.']),
            ],
        ])
        ->add('name', null, [
            'constraints' => [
                new NotBlank([
                    'message' => 'Veuillez saisir votre nom'
                ]),
            ],
        ])
        ->add('lastname', null, [
            'constraints' => [
                new NotBlank([
                    'message' => 'Veuillez saisir votre prenom'
                ]),
            ],
        ])
        
        ->add('password',RepeatedType::class, [
            'type'=>PasswordType::class,
            'first_options'=>['label'=>'Password'],
            'second_options'=>['label'=>'Confirm Password'],
            'constraints' => [
                new NotBlank(['message' => 'Veuillez saisir un mot de passe.']),
                new Length([
                    'min' => 6,
                    'max' => 4096,
                    'minMessage' => 'Le mot de passe doit contenir au moins {{ limit }} caractères.',
                    'maxMessage' => 'Le mot de passe ne doit pas contenir plus de {{ limit }} caractères.',
                ]),
            ]
        ])
    ;
    }

    public function configureOptions(OptionsResolver $resolver): void
    {
        $resolver->setDefaults([
            // Configure your form options here
        ]);
    }
}
